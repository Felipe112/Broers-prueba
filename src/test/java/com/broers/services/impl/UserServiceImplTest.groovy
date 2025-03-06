package com.broers.services.impl

import com.broers.common.Constants
import com.broers.common.JsonWebToken
import com.broers.controller.request.UserRegisterRequest
import com.broers.exceptions.EmailAlreadyExistsException

import com.broers.exceptions.UserNotFoundException
import com.broers.model.EmailModel
import com.broers.model.Token
import com.broers.model.TokenType
import com.broers.model.User
import com.broers.repositories.UserRepository
import com.broers.services.EmailService
import com.broers.services.TokenService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@SpringBootTest
class UserServiceImplSpec extends Specification {

  def userRepository = Mock(UserRepository)
  def tokenService = Mock(TokenService)
  def emailService = Mock(EmailService)
  def passwordEncoder = Mock(PasswordEncoder)
  def jsonWebToken = Mock(JsonWebToken)

  def userService = new UserServiceImpl(userRepository, tokenService, emailService, passwordEncoder, jsonWebToken)

  def "Debería registrar un usuario correctamente"() {
    given: "Una solicitud de registro de usuario"
    def request = new UserRegisterRequest(name: "Juan", email: "juan@example.com")

    and: "El correo no está registrado"
    userRepository.existsByEmail(request.email) >> false

    and: "El usuario se guarda en la base de datos"
    def user = new User(name: request.name, email: request.email, active: false)
    userRepository.save(_) >> user

    and: "Se genera un token de registro"
    def token = new Token(value: "12345", user: user, type: TokenType.REGISTRATION)
    tokenService.generateToken(_, TokenType.REGISTRATION) >> token

    when: "Se registra el usuario"
    def resultado = userService.registerUser(request)

    then: "El usuario se registra correctamente"
    resultado != null
    resultado.name == "Juan"
    resultado.email == "juan@example.com"

    and: "Se envía un correo con el token de activación"
    1 * emailService.sendEmail(_ as EmailModel)
  }

  def "No debería registrar un usuario si el email ya está registrado"() {
    given: "Un email que ya existe en la base de datos"
    def request = new UserRegisterRequest(name: "Pedro", email: "pedro@example.com")
    userRepository.existsByEmail(request.email) >> true

    when: "Se intenta registrar el usuario"
    userService.registerUser(request)

    then: "Se lanza una excepción EmailAlreadyExistsException"
    thrown(EmailAlreadyExistsException)
  }

  def "Debería activar un usuario correctamente"() {
    given: "Un token de activación válido"
    def user = new User(email: "test@example.com", active: false)
    def token = new Token(value: "abc123", user: user, type: TokenType.REGISTRATION)
    tokenService.validateToken("abc123", TokenType.REGISTRATION) >> token

    and: "El password encoder retorna una contraseña encriptada"
    passwordEncoder.encode(Constants.PASSWORD_DEFAULT) >> "hashed_password"

    when: "Se activa el usuario"
    def resultado = userService.activateUser("abc123")

    then: "El usuario se activa correctamente"
    resultado.active == true
    resultado.password == Constants.PASSWORD_DEFAULT

    and: "El token se marca como usado"
    1 * tokenService.saveToken(_ as Token)
  }

  def "Debería lanzar excepción si el usuario no existe al solicitar reset de contraseña"() {
    given: "Un email inexistente"
    userRepository.findByEmail("desconocido@example.com") >> Optional.empty()

    when: "Se solicita el reseteo de contraseña"
    userService.requestPasswordReset("desconocido@example.com")

    then: "Se lanza una excepción UserNotFoundException"
    thrown(UserNotFoundException)
  }

  def "Debería actualizar la contraseña correctamente"() {
    given: "Un token de reseteo válido"
    def user = new User(email: "user@example.com", password: "old_password")
    def token = new Token(value: "reset123", user: user, type: TokenType.PASSWORD_RESET)
    tokenService.validateToken("reset123", TokenType.PASSWORD_RESET) >> token

    and: "El password encoder no coincide con la nueva contraseña"
    passwordEncoder.matches("new_password", user.password) >> false
    passwordEncoder.encode("new_password") >> "hashed_new_password"

    when: "Se actualiza la contraseña"
    userService.updatePassword("reset123", "new_password")

    then: "La contraseña se actualiza correctamente"
    1 * userRepository.save(_ as User)

    and: "El token se marca como usado"
    1 * tokenService.saveToken(_ as Token)
  }

}
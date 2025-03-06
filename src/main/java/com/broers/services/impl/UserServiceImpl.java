package com.broers.services.impl;

import com.broers.common.Constants;
import com.broers.common.JsonWebToken;
import com.broers.controller.request.UserRegisterRequest;
import com.broers.exceptions.EmailAlreadyExistsException;
import com.broers.exceptions.InvalidPasswordException;
import com.broers.exceptions.UserBadRequestException;
import com.broers.exceptions.UserNotFoundException;
import com.broers.model.EmailModel;
import com.broers.model.Token;
import com.broers.model.TokenType;
import com.broers.model.User;
import com.broers.repositories.UserRepository;
import com.broers.services.EmailService;
import com.broers.services.TokenService;
import com.broers.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar los usuarios.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  /// / / DEPENDENCIAS /

  private final UserRepository userRepository;

  private final TokenService tokenService;

  private final EmailService emailService;

  private final PasswordEncoder passwordEncoder;

  private final JsonWebToken jsonWebToken;

  /// / / MÉTODOS PUBLICO /

  @Override
  public User registerUser(UserRegisterRequest request) {

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new EmailAlreadyExistsException(
          "El correo " + request.getEmail() + " ya está registrado");
    }

    try {
      User user = new User();
      user.setName(request.getName());
      user.setEmail(request.getEmail());
      user.setActive(false);
      user = userRepository.save(user);

      Token token = tokenService.generateToken(user, TokenType.REGISTRATION);
      emailService.sendEmail(EmailModel.builder().email(user.getEmail()).token(token.getValue())
          .subject(Constants.SUBJECT_ACTIVE).text(Constants.TEXT_ACTIVE).build());

      return user;

    } catch (Exception exc) {
      throw new UserBadRequestException(Constants.ERROR_RESPONSE + exc.getMessage());
    }
  }

  @Override
  public User activateUser(String tokenValue) {

    Token token = tokenService.validateToken(tokenValue, TokenType.REGISTRATION);
    User user = token.getUser();

    user.setPassword(passwordEncoder.encode(Constants.PASSWORD_DEFAULT));
    user.setActive(true);
    userRepository.save(user);

    token.setUsed(true);
    tokenService.saveToken(token);

    user.setPassword(Constants.PASSWORD_DEFAULT);
    return user;
  }

  @Override
  public void requestPasswordReset(String email) {

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

    tokenService.invalidatePreviousTokens(user, TokenType.PASSWORD_RESET);

    Token token = tokenService.generateToken(user, TokenType.PASSWORD_RESET);

    emailService.sendEmail(EmailModel.builder().email(user.getEmail()).token(token.getValue())
        .subject(Constants.SUBJECT_RESET).text(Constants.TEXT_RESET).build());
  }

  @Override
  public void updatePassword(String tokenValue, String newPassword) {

    Token token = tokenService.validateToken(tokenValue, TokenType.PASSWORD_RESET);

    User user = token.getUser();

    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      throw new InvalidPasswordException("La nueva contraseña debe ser diferente");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);

    token.setUsed(true);
    tokenService.saveToken(token);

  }

}

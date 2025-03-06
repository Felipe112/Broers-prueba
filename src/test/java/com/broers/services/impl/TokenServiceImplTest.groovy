package com.broers.services.impl

import com.broers.exceptions.InvalidTokenException
import com.broers.model.Token
import com.broers.model.TokenType
import com.broers.repositories.TokenRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
class TokenServiceImplTest extends Specification  {
  def tokenRepository = Mock(TokenRepository)
  def tokenService = new TokenServiceImpl(tokenRepository)


  def "validateToken should return a valid token"() {
    given:
    def tokenValue = "valid-token"
    def token = new Token(value: tokenValue, type: TokenType.REGISTRATION, expiryDate: LocalDateTime.now().plusHours(1), used: false)
    tokenRepository.findByValueAndUsed(tokenValue, false) >> Optional.of(token)

    when:
    def result = tokenService.validateToken(tokenValue, TokenType.REGISTRATION)

    then:
    result == token
  }

  def "validateToken should throw exception if token is not found"() {
    given:
    def tokenValue = "invalid-token"
    tokenRepository.findByValueAndUsed(tokenValue, false) >> Optional.empty()

    when:
    tokenService.validateToken(tokenValue, TokenType.REGISTRATION)

    then:
    thrown(InvalidTokenException)
  }

  def "validateToken should throw exception if token type is incorrect"() {
    given:
    def tokenValue = "wrong-type-token"
    def token = new Token(value: tokenValue, type: TokenType.PASSWORD_RESET, expiryDate: LocalDateTime.now().plusHours(1), used: false)
    tokenRepository.findByValueAndUsed(tokenValue, false) >> Optional.of(token)

    when:
    tokenService.validateToken(tokenValue, TokenType.REGISTRATION)

    then:
    thrown(InvalidTokenException)
  }

  def "validateToken should throw exception if token is expired"() {
    given:
    def tokenValue = "expired-token"
    def token = new Token(value: tokenValue, type: TokenType.REGISTRATION, expiryDate: LocalDateTime.now().minusHours(1), used: false)
    tokenRepository.findByValueAndUsed(tokenValue, false) >> Optional.of(token)

    when:
    tokenService.validateToken(tokenValue, TokenType.REGISTRATION)

    then:
    thrown(InvalidTokenException)
  }

}

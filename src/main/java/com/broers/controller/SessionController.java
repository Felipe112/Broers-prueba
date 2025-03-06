package com.broers.controller;

import com.broers.common.JsonWebToken;
import com.broers.controller.request.LoginRequest;
import com.broers.controller.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de session.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@RestController
@RequestMapping("/api/v1/session")
@RequiredArgsConstructor
@Tag(name = "Session", description = "Endpoints para la autenticación de usuarios")
public class SessionController {

  ////
  //// DEPENDENCIAS
  ////

  private final AuthenticationManager authenticationManager;
  private final JsonWebToken jwtUtil;

  ////
  //// MÉTODOS PÚBLICOS
  ////

  @PostMapping("/login")
  @Operation(summary = "Autenticar usuario",
      description = "Inicia sesión con email y contraseña, devolviendo un token JWT.")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new LoginResponse(jwt));
  }

}
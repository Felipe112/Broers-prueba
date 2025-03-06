package com.broers.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase configuraciones de seguridad.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  ////
  //// MÉTODOS PÚBLICOS
  ////

  /**
   * Configuración del AuthenticationManager
   *
   * @param authConfig
   *
   * @return
   *
   * @throws Exception
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {

    return authConfig.getAuthenticationManager();
  }

  /**
   * Configuración de seguridad HTTP
   *
   * @param http
   *
   * @return
   *
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs
        .authorizeHttpRequests(auth -> auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**", "/api/v1/users/register", "/api/v1/users/activate",
            "/api/v1/users/password-reset", "/api/v1/users/update-password",
            "/api/v1/session/login").permitAll().anyRequest().authenticated());
    return http.build();
  }

  /**
   * Codificador de contraseñas
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

}

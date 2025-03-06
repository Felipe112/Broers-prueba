package com.broers.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase configuraciones de swagger(documentacion del api).
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {

    return new OpenAPI().info(new Info().title("User Management API").version("1.0")
        .description("API para registro y autenticación de usuarios"));
  }

}



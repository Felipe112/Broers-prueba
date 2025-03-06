package com.broers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del sistema.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@SpringBootApplication
public class UsersApplication {

  ////
  //// MÉTODOS PÚBLICOS
  ////

  /**
   * Metodo inicializador del programa.
   *
   * @param args Parametros.
   */
  public static void main(String[] args) {

    SpringApplication.run(UsersApplication.class, args);
  }

}

# BROERS - Prueba T�cnica Java Developer
Este proyecto es una prueba t�cnica para el rol de Java Developer en BROERS.

## Requisitos previos
Antes de ejecutar el proyecto, aseg�rate de tener instalado:

* Docker (para la base de datos)
* Maven
* Java 17

## Configuraci�n y ejecuci�n
1. Iniciar la base de datos
   El proyecto usa PostgreSQL, el cual puedes iniciar con Docker ejecutando el siguiente comando en la ra�z del proyecto:
   ***
        docker-compose up -d
   ***
Esto levantar� la base de datos definida en el archivo docker-compose.yml.

*Nota: El archivo data.sql, ubicado en los recursos del proyecto, contiene los scripts de inicializaci�n de la base de datos.*

2. Compilar y ejecutar el proyecto
   Para ejecutar la aplicaci�n, usa Maven:
   ***
        mvn spring-boot:run
   ***
3. Acceder a la API
   Una vez iniciada la aplicaci�n, puedes acceder a la documentaci�n de la API en tu navegador:
   Swagger UI
   ***
       http://localhost:8081/api/v1/swagger-ui/index.html
   ***


# BROERS - Prueba Técnica Java Developer
Este proyecto es una prueba técnica para el rol de Java Developer en BROERS.

## Requisitos previos
Antes de ejecutar el proyecto, asegúrate de tener instalado:

* Docker (para la base de datos)
* Maven
* Java 17

## Configuración y ejecución
1. Iniciar la base de datos
   El proyecto usa PostgreSQL, el cual puedes iniciar con Docker ejecutando el siguiente comando en la raíz del proyecto:
   ***
        docker-compose up -d
   ***
Esto levantará la base de datos definida en el archivo docker-compose.yml.

*Nota: El archivo data.sql, ubicado en los recursos del proyecto, contiene los scripts de inicialización de la base de datos.*

2. Compilar y ejecutar el proyecto
   Para ejecutar la aplicación, usa Maven:
   ***
        mvn spring-boot:run
   ***
3. Acceder a la API
   Una vez iniciada la aplicación, puedes acceder a la documentación de la API en tu navegador:
   Swagger UI
   ***
       http://localhost:8081/api/v1/swagger-ui/index.html
   ***


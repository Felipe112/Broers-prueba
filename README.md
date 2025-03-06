# BROERS
Prueba tecnica Java Developer para BROERS

1. Crear la base de datos postgresql.
    * Ejecutar en docker la el *docker-compose.yml*
     ***
        docker-compose up
     ***
    * En los recursos del proyecto esta el archivo *data.sql* con los scrips para la base de datos.
2. Para ejecutar el proyecto se debe tener previamente instalado maven y Java 17.
3. El comando para subir el proyecto es 
    ***
        mvn spring-boot:run
    ***
4. En el navegador de confianza puede visualizar el api mediante el enpoint.
    ***
       http://localhost:8081/api/v1/swagger-ui/index.html
    ***

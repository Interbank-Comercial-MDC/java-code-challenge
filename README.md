# Proyecto de Microservicios

Este proyecto contiene dos microservicios: `ms-transaction` y `ms-anti-fraud`. A continuación, se detallan los pasos para ejecutar el proyecto utilizando Docker y acceder a la documentación Swagger de `ms-transaction`.

## Requisitos Previos

- [Docker](https://www.docker.com/get-started) instalado en tu máquina.
- [Docker Compose](https://docs.docker.com/compose/install/) instalado en tu máquina.

## Pasos para Ejecutar el Proyecto

# **Clona el Repositorio**

# **Construye y Levanta los Contenedores**

    Ejecuta el siguiente comando en el directorio raíz del proyecto, donde se encuentra el archivo docker-compose.yml:

docker-compose up --build
	
	Este comando construirá las imágenes necesarias y levantará los contenedores definidos en el archivo docker-compose.yml.

# **Verifica que los Servicios Estén Corriendo**

Puedes verificar que los contenedores están corriendo usando:

docker ps

Deberías ver contenedores para postgres, zookeeper, kafka, ms-transaction y ms-anti-fraud.

# **Accede a la Documentación Swagger de ms-transaction**

La documentación Swagger para el microservicio ms-transaction está disponible en la siguiente URL:

http://localhost:8181/webjars/swagger-ui/index.html

Accede a esta URL en tu navegador para ver todos los endpoints disponibles y sus detalles.

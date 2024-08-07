# Usa una imagen base de Maven para construir el proyecto
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usa una imagen base de OpenJDK para ejecutar el proyecto
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/challenge-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
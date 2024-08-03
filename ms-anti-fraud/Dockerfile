FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ms-anti-fraud/build/libs/ms-anti-fraud.jar ms-anti-fraud.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ms-anti-fraud.jar"]
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ms-transaction/build/libs/ms-transaction.jar ms-transaction.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ms-transaction.jar"]

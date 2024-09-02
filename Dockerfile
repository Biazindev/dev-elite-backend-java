FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /target/moviesList-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

RUN ls -la /app/target

FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/moviesList-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

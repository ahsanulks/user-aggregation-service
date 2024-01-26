# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn package

# Stage 2: Build the final image with GraalVM
FROM ghcr.io/graalvm/graalvm-ce:latest

WORKDIR /app

# Copy the JAR from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

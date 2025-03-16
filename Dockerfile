# Stage 1: Build JAR using Maven
FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/SmartITaxAPI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
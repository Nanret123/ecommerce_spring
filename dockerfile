# Stage 1: Build with Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose the Render-provided port
EXPOSE $PORT

# Run the app on Render's dynamic port
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
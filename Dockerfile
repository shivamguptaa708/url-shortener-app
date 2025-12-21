# Java runtime image
FROM eclipse-temurin:17-jre

# App directory inside container
WORKDIR /app

# Copy jar from target folder
COPY target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java","-jar","app.jar"]

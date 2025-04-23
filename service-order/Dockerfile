# 1. Use a base image with Java
FROM openjdk:17-jdk-slim

# 2. Set the working directory in the container
WORKDIR /app

# 3. Copy the JAR file from your build folder
COPY target/service-order-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose port (same as your app's server.port, usually 8080)
EXPOSE 8080

# 5. Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# 1. Use a base image with Java
FROM openjdk:17-jdk-slim

# 2. Set the working directory in the container
WORKDIR /app

# 3. Copy the JAR file from your build folder
COPY service-inventory/target/inventory-service.jar inventory-service.jar

# 4. Expose port (same as your app's server.port, usually 8080)
EXPOSE 8082

# 5. Run the application
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]

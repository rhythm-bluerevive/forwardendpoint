# Use a lightweight JDK base image
FROM openjdk:21-jdk-slim

# Set environment variable for timezone or other configs (optional)
ENV TZ=Asia/Kolkata

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Set the default command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]

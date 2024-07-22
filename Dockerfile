# Use an official Maven image with OpenJDK 17 to build the application
FROM maven:3.8.1-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Package the application and skip tests
RUN mvn clean package -DskipTests

# Debug: Verify the JAR file was created
RUN ls -l target/

# Use an official OpenJDK runtime image with Java 17 for the final build
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/rihal-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

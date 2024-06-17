# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /bank-application

# Copy the Maven POM file into the container
COPY pom.xml .
#
## Download dependencies and build application
#RUN mvn clean install -DskipTest

# Copy the Spring Boot application JAR file into the container
COPY target/bank-application-1.0.jar /bank-application/bank-application.jar

# Copy the application.properties or application.yml file into the container
COPY application.yml /bank-application/application.yml

# Expose the port that your application runs on
EXPOSE 8081

# Command to run the Spring Boot application
CMD ["java", "-jar", "bank-application.jar", "--spring.config.location=file:/bank-application/application.yml"]

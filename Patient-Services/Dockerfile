# ----------- Stage 1: Build the application -------------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the project
COPY src ./src
RUN mvn clean package -DskipTests

# ----------- Stage 2: Run the application -------------
FROM openjdk:21-jdk AS runner

# Set working directory for the final image
WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder /app/target/Patient-Services-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

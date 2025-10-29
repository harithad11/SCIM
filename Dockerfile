# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app


# Make mvnw executable
RUN chmod +x ./mvnw

# Install the external JAR into local Maven repo
RUN ./mvnw install:install-file \
  -Dfile=libs/scim-server-sdk-03.00.03.jar \
  -DgroupId=com.okta.scim \
  -DartifactId=scim-server-sdk \
  -Dversion=03.00.03 \
  -Dpackaging=jar



# Build the project (skip tests for faster CI/CD)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/oktascim-0.0.1-SNAPSHOT.jar"]

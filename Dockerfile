# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy only what's needed first (for better caching)
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY libs libs

# Make mvnw executable
RUN chmod +x ./mvnw

# Install the external JAR into local Maven repo
RUN ./mvnw install:install-file \
  -Dfile=libs/scim-server-sdk-03.00.03.jar \
  -DgroupId=com.okta.scim \
  -DartifactId=scim-server-sdk \
  -Dversion=03.00.03 \
  -Dpackaging=jar

# Download dependencies first (cached for faster rebuilds)
RUN ./mvnw dependency:go-offline

# Copy the rest of the source code
COPY src src

# Build the project (skip tests for faster CI/CD)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot JAR
CMD ["java", "-jar", "target/oktascim-0.0.1-SNAPSHOT.jar"]

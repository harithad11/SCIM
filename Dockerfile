FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .

# Make mvnw executable
RUN chmod +x ./mvnw

# Build the project
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/oktascim-0.0.1-SNAPSHOT.jar"]

# --- BUILD STAGE ---
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean bootJar --no-daemon

# --- RUNTIME STAGE ---
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/build/libs/file-scanner-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

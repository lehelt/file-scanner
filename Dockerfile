# --- BUILD STAGE ---
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN chmod +x ./gradlew
RUN ls -al
RUN ./gradlew clean bootJar --no-daemon

# --- RUNTIME STAGE ---
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Build-time arg for port number
ARG SERVER_PORT=8080
# Runtime env variable
ENV SERVER_PORT=${SERVER_PORT}

COPY --from=builder /app/build/libs/*.jar app.jar

# EXPOSE
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java","-jar","/app/app.jar"]

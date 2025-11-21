# File Scanner

A small Spring Boot service that scans for files with a chosen extension and records findings. 

## Prerequisites
- Java 21 (JDK)
- Gradle (wrapper included: `gradlew`/`gradlew.bat`)
- One of:
  - Podman + Podman Compose

## Configure
Default settings live in `src/main/resources/application.properties`. Most values are driven by environment variables at runtime:
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- `SERVER_PORT` (default 8080)
- `APP_USER` (label for the running instance)

## Start

### 1) Makefile (Podman)
```bash
make run
```
This builds the app and runs `podman compose up --build`.

### 2) Local Gradle (uses your own PostgreSQL, dev purposes)
Ensure a PostgreSQL instance is reachable and export env vars:
```bash
# PowerShell example
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/file_scanner_db"
$env:SPRING_DATASOURCE_USERNAME = "postgres"
$env:SPRING_DATASOURCE_PASSWORD = "postgres"
$env:SERVER_PORT = "8080"

# Start the app
./gradlew bootRun
```

## Stop

- Makefile:
```bash
make stop
```
- Local Gradle:
Stop the process in your terminal (Ctrl+C).







APP_NAME=file-scanner
GRADLE_CMD = gradlew.bat
ifeq ($(OS),)
	GRADLE_CMD = ./gradlew
endif

build:
	$(GRADLE_CMD) clean build
# Build and run via docker-compose.yml
run: build
	podman compose up --build

# Stop all containers
stop:
	echo "Stopping containers..."
	podman compose down

# Rebuild and restart everything
rebuild:
	stop build run
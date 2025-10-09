APP_NAME=file-scanner

build:
	echo "Building..."
	podman build -t $(APP_NAME) .

# Build and run via docker-compose.yml
run:
	echo "Starting containers..."
	podman compose up -d --build

# Stop all containers
stop:
	echo "Stopping containers..."
	podman compose down

# Rebuild and restart everything
rebuild:
	stop build run
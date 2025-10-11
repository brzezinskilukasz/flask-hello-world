# Variables
APP_NAME := flask-hello-world
DOCKER_IMAGE := $(APP_NAME):latest

# Application commands
# Install dependencies
install:
	# Install development dependencies
	pip install -r requirements-dev.txt

# Run the application
run:
	python src/app.py

# Build the Docker image
build:
	docker build -t $(DOCKER_IMAGE) .

# Run the Docker container
run-docker:
	docker run -p 5000:5000 $(DOCKER_IMAGE)

# Lint the code
lint:
	pylint src/ tests/
	
# Clean up Docker images and containers
clean:
	docker rm -f $(shell docker ps -aq) || true
	docker rmi -f $(shell docker images -q $(DOCKER_IMAGE)) || true

# Run tests (placeholder for future tests)
test:
	PYTHONPATH=src python -m unittest discover -s tests


# Helm commands
# Lint the Helm chart
helm-lint:
	helm lint ./charts/flask-hello-world

# Render the Helm templates
helm-template:
	helm template my-release ./charts/flask-hello-world

# Render the Helm templates in debug mode (shows templates rendered even if they are invalid)
helm-template-debug:
	helm template my-release ./charts/flask-hello-world --debug

# Package the Helm chart
helm-package:
	helm package ./charts/flask-hello-world

# Install the Helm chart to the Kubernetes cluster
helm-install:
	helm install my-release ./charts/flask-hello-world

# Upgrade the Helm release (if already installed) on the Kubernetes cluster
helm-upgrade:
	helm upgrade my-release ./charts/flask-hello-world

# Uninstall the Helm release from the Kubernetes cluster
helm-uninstall:
	helm uninstall my-release

# Run Helm tests (if defined in the chart)
helm-test:
	helm test my-release


# Phony targets
.PHONY: install run build run-docker lint clean test helm-lint helm-template helm-template-debug helm-package helm-install helm-upgrade helm-uninstall helm-test
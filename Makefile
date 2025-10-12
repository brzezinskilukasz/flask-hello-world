# Variables
APP_NAME := flask-hello-world
IMAGE_REGISTRY := localhost:5000
HELM_REGISTRY ?= oci://localhost:5000/helm
GIT_COMMIT := $(shell git rev-parse --short HEAD)
GIT_BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
DOCKER_IMAGE := $(APP_NAME):$(GIT_BRANCH)-$(GIT_COMMIT)

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
	@echo "🐳 Building Docker image $(DOCKER_IMAGE)"
	docker build -t $(DOCKER_IMAGE) .

# Run the Docker container
run-docker:
	docker run -p 5000:5000 $(DOCKER_IMAGE)

# Lint the code
lint:
	@echo "🔍 Running pylint..."
	pylint --fail-under=9.0 src/ tests/
	
# Clean up Docker images and containers
clean:
	docker rm -f $(shell docker ps -aq) || true
	docker rmi -f $(shell docker images -q $(DOCKER_IMAGE)) || true

# Run tests (placeholder for future tests)
test:
	@echo "🧪 Running tests..."
	PYTHONPATH=src python -m unittest discover -s tests

push-image:
	@echo "🚀 Pushing Docker image $(DOCKER_IMAGE) to $(IMAGE_REGISTRY)"
	docker tag $(DOCKER_IMAGE) $(IMAGE_REGISTRY)/$(DOCKER_IMAGE)
	docker push $(IMAGE_REGISTRY)/$(DOCKER_IMAGE)


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
	helm package ./charts/flask-hello-world --destination ./charts/flask-hello-world/charts

# Push the Helm chart to a chart repository
helm-push:
	@echo "Pushing Helm chart to repository..."
	helm push ./charts/flask-hello-world/charts/*.tgz $(HELM_REGISTRY)

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


# ===================================================
# 🧱 CI-ONLY TARGETS — Do not run locally
# These are invoked by Jenkins pipelines only.
# A production setup would require authentication to push images
# thus only allowing these targets to be run in CI.
# ===================================================
ci-promote-preprod:
	@echo "Promoting to preprod..."
	docker tag $(DOCKER_IMAGE) $(IMAGE_REGISTRY)/$(APP_NAME):preprod-$(GIT_COMMIT)
	docker push $(IMAGE_REGISTRY)/$(APP_NAME):preprod-$(GIT_COMMIT)

ci-promote-prod:
	@echo "Promoting to prod..."
	docker tag $(DOCKER_IMAGE) $(IMAGE_REGISTRY)/$(APP_NAME):prod-$(GIT_COMMIT)
	docker push $(IMAGE_REGISTRY)/$(APP_NAME):prod-$(GIT_COMMIT)
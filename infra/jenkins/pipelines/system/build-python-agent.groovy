def dockerfile = 'Dockerfile.build-python3-make'
def registry = 'localhost:5000'
def imageName = 'jenkins/inbound-agent'
def imageTag = 'jdk17-helm-make'

load 'infra/jenkins/pipelines/templates/build-agent-template.groovy' with parameters: [
    DOCKERFILE_NAME: dockerfile,
    IMAGE_NAME: imageName,
    IMAGE_TAG: imageTag,
    REGISTRY: registry
]
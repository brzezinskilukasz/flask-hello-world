def dockerfile = 'Dockerfile.build-python3-make'
def registry = 'localhost:5000'
def imageName = 'jenkins/inbound-agent'
def imageTag = 'jdk17-helm-make'

load 'infra/jenkins/pipelines/templates/build-agent-template.groovy'

pipeline {
    agent any
    stages {
        stage('Build and Push Python Agent') {
            steps {
                build job: 'pipelines/templates/build-agent-template', parameters: [
                    string(name: 'DOCKERFILE', value: dockerfile),
                    string(name: 'IMAGE_NAME', value: imageName),
                    string(name: 'IMAGE_TAG', value: imageTag),
                    string(name: 'REGISTRY', value: registry)
                ]
            }
        }
    }
}
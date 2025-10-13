pipeline {
    agent { label 'cloud-python' } //  This agent has access to docker - for production consider a dedicated agent pool

    parameters {
        string(name: 'DOCKERFILE_NAME', defaultValue: 'Dockerfile', description: 'Name of the Dockerfile to use for building the image')
        string(name: 'IMAGE_NAME', description: 'Name of the Docker image to build')
        string(name: 'IMAGE_TAG', description: 'Tag for the Docker image')
        string(name: 'REGISTRY', defaultValue: 'https://secure-internal-registry.corp', description: 'Docker registry to push the image to')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageName = "${params.REGISTRY}/${params.IMAGE_NAME}:${params.IMAGE_TAG}"
                    sh "docker build -f ${params.DOCKERFILE_NAME} -t ${imageName} ."
                }

                script {
                    sh "docker push ${params.REGISTRY}/${params.IMAGE_NAME}:${params.IMAGE_TAG}"
                }
            }
        }

        stage('Push to Registry') {
            steps {
                script {
                    def imageName = "${params.REGISTRY}/${params.IMAGE_NAME}:${params.IMAGE_TAG}"
                    sh "docker push ${imageName}"
                }
            }
        }
    }
}
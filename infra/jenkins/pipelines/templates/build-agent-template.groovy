/**
 * Jenkins Pipeline Template: Build and Push Jenkins Agent Docker Image
 *
 * This script provides reusable functions for building and pushing Docker images for Jenkins agents.
 *
 * Functions:
 * - loginToDockerRegistry(Map args = [:]): Logs into a Docker registry.
 *   Parameters:
 *     - registry (String): Docker registry to log in to (default: 'localhost:5000').
 *     - username (String): Docker registry username (default: '').
 *     - password (String): Docker registry password (default: '').
 *
 * - buildAgentImage(Map args = [:]): Builds a Docker image for a Jenkins agent.
 *   Parameters:
 *     - imageName (String): Name of the Docker image (default: 'jenkins/custom-jenkins-agent').
 *     - dockerfilePath (String): Path to the Dockerfile (default: 'infra/jenkins/agents/Dockerfile').
 *     - imageTag (String): Tag for the Docker image (default: 'latest').
 *
 * - pushAgentImage(Map args = [:]): Pushes the built Docker image to a Docker registry.
 *   Parameters:
 *     - imageName (String): Name of the Docker image (default: 'jenkins/custom-jenkins-agent').
 *     - imageTag (String): Tag for the Docker image (default: 'latest').
 *     - registry (String): Docker registry to push the image to (default: 'localhost:5000').
 *
 * Usage:
 * This script is intended to be loaded and used in other Jenkins pipeline scripts.
 * Example:
 *   def buildAgent = load 'path/to/build-agent-template.groovy'
 *   buildAgent.loginToDockerRegistry(registry: 'my-registry.com', username: 'my-username', password: 'my-password')
 *   buildAgent.buildAgentImage(imageName: 'my-agent', dockerfilePath: 'path/to/Dockerfile', imageTag: 'v1.0')
 *   buildAgent.pushAgentImage(imageName: 'my-agent', imageTag: 'v1.0', registry: 'my-registry.com')
 */

def loginToDockerRegistry(Map args = [:]) {
    def registry = args.get('registry', 'localhost:5000')
    def username = args.get('username', '')
    def password = args.get('password', '')

    if (username && password) {
        stage("Login to Docker Registry: ${registry}") {
            script {
                def loginCommand = "echo ${password} | docker login ${registry} --username ${username} --password-stdin"
                echo "Executing command: ${loginCommand}"
                // sh loginCommand
                echo "Simulated Docker login command execution using provided credentials: Username: ${username}, Password: ${password}"
                echo "The line above is for demo purposes only. Never log sensitive information in real scripts."
            }
        }
    } else {
        echo "Skipping Docker registry login as username or password is not provided."
    }
}

def buildAgentImage(Map args = [:]) {
    def imageName = args.get('imageName', 'jenkins/custom-jenkins-agent')
    def dockerfilePath = args.get('dockerfilePath', 'infra/jenkins/agents/Dockerfile')
    def imageTag = args.get('imageTag', 'latest')

    stage("Build Jenkins Agent Image: ${imageName}") {
        script {
            def buildCommand = "docker build -t ${imageName}:${imageTag} -f ${dockerfilePath} ."
            echo "Executing command: ${buildCommand}"
            sh buildCommand
        }
    }
}

def pushAgentImage(Map args = [:]) {
    def imageName = args.get('imageName', 'jenkins/custom-jenkins-agent')
    def imageTag = args.get('imageTag', 'latest')
    def registry = args.get('registry', 'localhost:5000')

    stage("Push Jenkins Agent Image: ${imageName} to ${registry}") {
        script {
            def fullImageName = "${registry}/${imageName}:${imageTag}"
            def tagCommand = "docker tag ${imageName}:${imageTag} ${fullImageName}"
            def pushCommand = "docker push ${fullImageName}"

            echo "Tagging image with command: ${tagCommand}"
            sh tagCommand

            echo "Pushing image with command: ${pushCommand}"
            sh pushCommand
        }
    }
}

// Combined function to login, build, and push the agent image
// This could be used to reduce boilerplate in Jenkinsfiles
// However it adds another layer of abstraction that might reduce clarity
// Therefore for this project a decision was made to use the individual functions
def loginBuildAndPushAgent(Map args = [:]) {
    loginToDockerRegistry(args)
    buildAgentImage(args)
    pushAgentImage(args)
}

return this
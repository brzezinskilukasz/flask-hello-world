// Create Jenkins pipeline jobs for the Flask application

def repoUrl = 'https://github.com/brzezinskilukasz/flask-hello-world.git'
def branch = 'jenkins-ci'


// --- Create flask-hello-world-ci Job ---
pipelineJob('flask-hello-world-ci') {
    description('CI Pipeline for Flask Hello World Application')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(repoUrl)
                        // credentials('github-credentials') // Use Jenkins credentials for GitHub access if needed
                    }
                    branches(branch)
                }
            }
        }
        scriptPath('ci-cd/jenkins/Jenkinsfile.ci')
    }
}

// --- Create flask-hello-world-cd Job ---
// TODO

// --- Create flask-hello-world-helm Job ---
pipelineJob('flask-hello-world-helm') {
    description('Helm Deployment Pipeline for Flask Hello World Application')

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(repoUrl)
                        // credentials('github-credentials') // Use Jenkins credentials for GitHub access if needed
                    }
                    branches(branch)
                }
            }
        }
        scriptPath('ci-cd/jenkins/Jenkinsfile.helm-ci')
    }
}
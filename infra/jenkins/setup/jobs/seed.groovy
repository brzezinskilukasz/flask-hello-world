pipeline {
    agent any

    stages {
        stage('Checkout infra repository') {
            steps {
                // Checkout the infra repository containing Jenkins setup and pipeline scripts
                // In our case, we have everything in one repository
                checkout([
                    $class: 'GitSCM',
                branches: [[name: 'jenkins-ci']],
                userRemoteConfigs: [[url: 'https://github.com/brzezinskilukasz/flask-hello-world.git']]
            ])
            }
        }

        stage('Generate flask-hello-world Jobs from Job DSL') {
            steps {
                // Load and execute the Job DSL script to create Jenkins jobs
                jobDsl(
                    targets: 'flask-hello-world/*.groovy',
                    failOnMissingPlugin: true,
                    ignoreExisting: false,
                    removedJobAction: 'IGNORE',
                    removedViewAction: 'IGNORE',
                    lookupStrategy: 'SEED_JOB' // Options: JENKINS_ROOT, SEED_JOB
                    unstableDeprecation: true
                )
            }
        }

        post {
            success {
                echo 'Jenkins jobs have been successfully created or updated.'
            }
            failure {
                echo 'There was an error creating or updating Jenkins jobs.'
            }
        }
    }
}
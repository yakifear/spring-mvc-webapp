pipeline {
    agent {
        label 'Linux'
    }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
      allure '3.16.0'
    }

    stages {
        stage('Test') {
            steps {
                sh 'cd /var/jenkins/workspace/cicd'
                sh 'rm -rf /var/jenkins/workspace/cicd/*'
                // sh 'allure --version'
            }
        }

        stage('Git') {
            steps {
                echo 'stage Git'
            }
        }

        stage('Build') {
            steps {
                echo 'sosil?'
            }
        }

        stage('Server') {
            steps {
                sh 'ls -a'
            }
        }
    }
}
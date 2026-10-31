pipeline {
    agent {
        label 'Linux'
    }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
    }

    stages {
        stage('Test') {
            steps {
                sh 'cd /var/jenkins/workspace/cicd'
                sh 'rm -rf /var/jenkins/workspace/cicd/*'
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
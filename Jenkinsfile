pipeline {
    agent {
        label 'Linux'
    }

    // options {
    //     skipDefaultCheckout(true)
    // }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
    }

    stages {
        stage('Test') {
            steps {
                sh 'cd /var/jenkins/workspace/cicd'
                sh 'ls -a'
                // checkout scm
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
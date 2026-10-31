pipeline {
    agent {
        label 'Linux'
    }

    options {
        skipDefaultCheckout(true)
    }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
    }

    stages {
        stage('Test') {
            steps {
                sh 'git --version'
                checkout scm
                sh 'mvn -v'
                sh 'ls -a'
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
                sh 'rm -rf spring-mvc-webapp'
                sh 'ls -a'
            }
        }
    }
}
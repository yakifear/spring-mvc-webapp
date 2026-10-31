pipeline {
    agent {
        label 'Linux'
    }

    options {
        skipDefaultCheckout(true)
    }

    tools {
      maven '3.10.0'
      git 'last'
    }

    stages {
        stage('Test') {
            steps {
                sh 'mvn -v'
                sh 'git --version'
                sh 'git clone https://github.com/yakifear/spring-mvc-webapp.git -b dev'
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
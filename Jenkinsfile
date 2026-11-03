pipeline {
    agent {
        label 'Linux'
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
                echo 'sosal?'
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
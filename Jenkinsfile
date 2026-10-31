pipeline {
    agent {
        label 'Linux'
    }

    options {
        skipDefaultCheckout(true)
    }

    tools {
      maven '3.10.0'
    //   git 'linux-git-install-last'
    }

    stages {
        stage('Test') {
            steps {
                checkout scmGit(
                    branches: [[name: 'refs/heads/dev']],
                    gitTool: 'linux-git-install-last',
                    userRemoteConfigs: [[
                        url: 'https://github.com/yakifear/spring-mvc-webapp.git'
                    ]]
                )
                sh 'mvn -v'
                sh 'git --version'
                // sh 'git clone https://github.com/yakifear/spring-mvc-webapp.git -b dev'
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
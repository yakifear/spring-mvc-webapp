pipeline {
    agent {
        label 'Linux'
    }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
      allure '3.16.0'
    }

    environment {
        IMAGE_NAME = 'peni5/webapp'
        VPS_HOST = '201.58.172.0'
        REMOTE_DIR = '/home/webapp'
    }

    stages {

        stage('Check') {
            steps {
                sh 'mvn --version'
                sh 'git --version'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('integration') {
            steps {
                echo 'zdez budet push v main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -DskipTests package'
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    sshUserPrivateKey(
                        credentialsId: 'server-ssh',
                        keyFileVariable: 'SSH_KEY',
                        usernameVariable: 'SSH_USER'
                    )
                ]) {
                    sh '''
                    set +x

                    echo "Key file size:"
                    wc -c < "$SSH_KEY"

                    echo "Key file header:"
                    head -n 1 "$SSH_KEY" | tr -d '\\r'

                    echo "Key validation:"
                    ssh-keygen -y -f "$SSH_KEY" >/dev/null

                    echo "Private key is valid!"

                    scp -i "$SSH_KEY" \
                        -o IdentitiesOnly=yes \
                        -o StrictHostKeyChecking=yes \
                        -o UserKnownHostsFile="$HOME/.ssh/known_hosts" \
                        "/var/jenkins/workspace/cicd/target/spring-mvc-app1.war" \
                        "$SSH_USER@$VPS_HOST:$REMOTE_DIR/spring-mvc.war"

                    scp -i "$SSH_KEY" \
                        -o IdentitiesOnly=yes \
                        -o StrictHostKeyChecking=yes \
                        -o UserKnownHostsFile="$HOME/.ssh/known_hosts" \
                        "/var/jenkins/workspace/cicd/Dockerfile" \
                        "$SSH_USER@$VPS_HOST:$REMOTE_DIR/Dockerfile"

                    ssh -i "$SSH_KEY" \
                        -o IdentitiesOnly=yes \
                        -o StrictHostKeyChecking=yes \
                        -o UserKnownHostsFile="$HOME/.ssh/known_hosts" \
                        "$SSH_USER@$VPS_HOST" \
                        "ls -lh $REMOTE_DIR/spring-mvc.war"
                    '''
                    }
                }
            }

            stage('Clear') {
                steps {
                    sh 'rm -rf /var/jenkins/workspace/cicd/*'
                }
            }

        }
    }
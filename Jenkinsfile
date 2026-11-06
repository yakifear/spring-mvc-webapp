pipeline {
    agent { 
        label 'Linux' 
    }

    tools {
        maven '3.10.0'
    }

    environment {
        VPS_HOST = '201.58.172.0'
        REMOTE_DIR = '/home/webapp/build'
        IMAGE_NAME = 'peni5/webapp'
        IMAGE_TAG = "build-${BUILD_NUMBER}"
    }

    stages {

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

        stage('Build WAR') {
            steps {
                sh '''
                    cp /home/database.properties \
                        src/main/resources/database.properties

                    echo "=== Building WAR ==="
                    mvn -DskipTests package
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    rm -rf image-context
                    mkdir -p image-context

                    cp target/spring-mvc-app1.war \
                        image-context/spring-mvc.war

                    cp Dockerfile image-context/Dockerfile

                    docker build \
                        -t "$IMAGE_NAME:$IMAGE_TAG" \
                        image-context

                    docker save \
                        -o image.tar \
                        "$IMAGE_NAME:$IMAGE_TAG"

                    test -s image.tar

                    docker image inspect \
                        "$IMAGE_NAME:$IMAGE_TAG" \
                        --format '{{.Id}}'
                '''
            }
        }

        stage('Prepare Deployment') {
            steps {
                sh '''
                    set -eu

                    sed "s|image: peni5/webapp:v2|image: ${IMAGE_NAME}:${IMAGE_TAG}|" \
                        webapp.yaml > webapp-deploy.yaml

                    mkdir -p "$HOME/.ssh"
                    chmod 700 "$HOME/.ssh"

                    ssh-keyscan -H -t ed25519 "$VPS_HOST" \
                        >> "$HOME/.ssh/known_hosts"

                    chmod 600 "$HOME/.ssh/known_hosts"
                '''
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
                        SSH_OPTS="-i $SSH_KEY \
                            -o IdentitiesOnly=yes \
                            -o StrictHostKeyChecking=yes \
                            -o UserKnownHostsFile=$HOME/.ssh/known_hosts"

                        ssh $SSH_OPTS "$SSH_USER@$VPS_HOST" \
                            "mkdir -p '$REMOTE_DIR'"

                        scp $SSH_OPTS image.tar \
                            "$SSH_USER@$VPS_HOST:$REMOTE_DIR/image.tar"

                        scp $SSH_OPTS webapp-deploy.yaml \
                            "$SSH_USER@$VPS_HOST:$REMOTE_DIR/webapp-deploy.yaml"

                        ssh $SSH_OPTS "$SSH_USER@$VPS_HOST" \
                            "cd '$REMOTE_DIR';

                             k3s ctr -n k8s.io images import image.tar;

                             kubectl apply -f webapp-deploy.yaml;

                             kubectl rollout status deployment/webapp \
                                 --timeout=180s;

                             rm -f image.tar webapp-deploy.yaml"
                    '''
                }
            }
        }

        stage('Clear') {
            steps {
                sh '''
                    docker image rm "$IMAGE_NAME:$IMAGE_TAG" || true
                    rm -rf image.tar image-context webapp-deploy.yaml
                    rm -rf /var/jenkins/workspace/cicd/*
                '''
            }
        }
    }
}

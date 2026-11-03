pipeline {
    agent {
        label 'Linux'
    }

    tools {
      maven '3.10.0'
      git 'linux-git-install-last'
    //   allure '3.16.0'
    }

    environment {
        VPS_HOST = '201.58.172.0'
        REMOTE_DIR = '/home/webapp/build'
        IMAGE_NAME = 'peni5/webapp'
        IMAGE_TAG = "build-${BUILD_NUMBER}"
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
                sh '''
                    mvn -DskipTests package

                    # Готовим Kubernetes-манифест с тегом текущей сборки
                    sed "s|image: peni5/webapp:v2|image: ${IMAGE_NAME}:${IMAGE_TAG}|" \
                        webapp.yaml > webapp-deploy.yaml

                    grep "image:" webapp-deploy.yaml
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
                ]) 
                {
                    sh '''
                    SSH_OPTS="-i $SSH_KEY \
                        -o IdentitiesOnly=yes \
                        -o StrictHostKeyChecking=yes \
                        -o UserKnownHostsFile=$HOME/.ssh/known_hosts"

                    ssh $SSH_OPTS \
                        "$SSH_USER@$VPS_HOST" \
                        "mkdir -p $REMOTE_DIR"

                    scp  $SSH_OPTS \
                        "/var/jenkins/workspace/cicd/target/spring-mvc-app1.war" \
                        "$SSH_USER@$VPS_HOST:$REMOTE_DIR/spring-mvc.war"

                    scp  $SSH_OPTS \
                        "/var/jenkins/workspace/cicd/Dockerfile" \
                        "$SSH_USER@$VPS_HOST:$REMOTE_DIR/Dockerfile"

                    scp $SSH_OPTS \
                        webapp-deploy.yaml \
                        "$SSH_USER@$VPS_HOST:$REMOTE_DIR/webapp-deploy.yaml"

                    ssh $SSH_OPTS \
                        "$SSH_USER@$VPS_HOST" \
                        "cd '$REMOTE_DIR';

                        docker build \
                            -t '$IMAGE_NAME:$IMAGE_TAG' \
                            -f Dockerfile \
                            .;

                        docker save \
                            -o image.tar \
                            '$IMAGE_NAME:$IMAGE_TAG';

                        k3s ctr -n k8s.io images import image.tar;

                        rm -f image.tar;

                        kubectl apply -f webapp-deploy.yaml;

                        kubectl rollout status \
                            deployment/webapp \
                            --timeout=180s;

                    ssh  $SSH_OPTS \
                        "$SSH_USER@$VPS_HOST" \
                        "rm -rf /home/webapp/build/*"
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
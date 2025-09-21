pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS = credentials('dockerhub-creds')
        DOCKER_IMAGE = "zeravatechnologies/closet-api"
        
     
        DOCKER_HOST = "tcp://host.docker.internal:2375"


    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/zeravatechnologies/closet-api.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t $DOCKER_IMAGE:dev ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    sh "echo $DOCKER_CREDENTIALS_PSW | docker login -u $DOCKER_CREDENTIALS_USR --password-stdin"
                    sh "docker push $DOCKER_IMAGE:dev"
                }
            }
        }

        stage('Deploy to Kubernetes (Dev)') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh """
                      kubectl set image deployment/closet-api closet-api=$DOCKER_IMAGE:dev -n closet-dev --record
                      kubectl rollout status deployment/closet-api -n closet-dev
                    """
                }
            }
        }
    }
}

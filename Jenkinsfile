pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:latest
    command:
    - cat
    tty: true
    volumeMounts:
    - name: kaniko-secret
      mountPath: /kaniko/.docker
  volumes:
  - name: kaniko-secret
    secret:
      secretName: dockerhub-creds
"""
        }
    }

    environment {
        DOCKER_IMAGE = "zeravatechnologies/closet-api"

    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/zeravatechnologies/closet-api.git'
            }
        }

        stage('Build & Push Image') {
            steps {
                container('kaniko') {
                    sh """
                      /kaniko/executor \
                        --context `pwd` \
                        --dockerfile `pwd`/Dockerfile \
                        --destination=$DOCKER_IMAGE:dev \
                        --cache=true
                    """
                }
            }
        }

        stage('Deploy to Kubernetes (Dev)') {
            steps {

                sh """
                  kubectl set image deployment/closet-api closet-api=$DOCKER_IMAGE:dev -n closet-dev
                  kubectl rollout status deployment/closet-api -n closet-dev
                """


            }
        }
    }
}

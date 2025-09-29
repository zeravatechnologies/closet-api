pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: jenkins-kaniko
spec:
  containers:
    - name: kaniko
      image: gcr.io/kaniko-project/executor:debug
      command:

        - cat

      tty: true
      volumeMounts:
        - name: docker-config
          mountPath: /kaniko/.docker
  
    - name: kubectl
      image: bitnami/:1.34.0   # ✅ use a kubectl image, match version to your cluster
      command:
        - cat
      tty: true    
  
  volumes:
    - name: docker-config
      projected:
        sources:
          - secret:
              name: dockerhub-creds
              items:
                - key: .dockerconfigjson
                  path: config.json
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

        stage('Build & Push with Kaniko') {
            steps {
                container('kaniko') {
                    sh """
                    /kaniko/executor \
                      --context . \
                      --dockerfile Dockerfile \
                      --destination=$DOCKER_IMAGE:dev \
                      --cleanup
                    """
                }
            }
        }

        stage('Deploy to Kubernetes (Dev)') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG_FILE')]) {
                    sh """
                      export KUBECONFIG=$KUBECONFIG_FILE
                      kubectl set image deployment/closet-api closet-api=$DOCKER_IMAGE:dev -n closet-dev
                      kubectl rollout status deployment/closet-api -n closet-dev
                    """
                }
            }
        }
    }
}

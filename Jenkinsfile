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
        - name: docker-config
          mountPath: /kaniko/.docker
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

                script {
                    // Save kubeconfig from Jenkins secret
                    writeFile file: 'kubeconfig.yaml', text: KUBECONFIG_CRED
                    sh """
                      export KUBECONFIG=kubeconfig.yaml
                      kubectl set image deployment/closet-api closet-api=$DOCKER_IMAGE:dev -n closet-dev
                      kubectl rollout status deployment/closet-api -n closet-dev
                    """
                }


            }
        }
    }
}

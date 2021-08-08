pipeline {
    agent any
    tools {
        maven 'maven3' //This should be defined in Global Tool Configuration maven installatons with name "maven3"
    }
	parameters {
		string(name: 'DOCKER_REPO_ID', defaultValue: 'docker-repo', description: 'Docker ID belong to the dockerhub repository')
// 		booleanParam(name: 'IS_CD_DIR', defaultValue: 'false', description: 'If changing to a directory required')
		string(name: 'CD_DIR', defaultValue: '.', description: 'The directory to change to, before build')
		string(name: 'MS_NAME', defaultValue: 'dynamic-scheduler', description: 'Name of the service - used to create the docker image')
		string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Docker tag name')
	}
	environment {
		//Create a Jenkins credential with the name dockerhub, save your username/password.
		DOCKER_CREDS = credentials('dockerhub')
	}
    stages {
        stage('QG1 - Unit Test') {
            steps {
                script {
                    dir(params.CD_DIR) {
                        sh 'mvn clean test'
                    }
                }
            }
        }
        stage('Package') {
            steps {
				script {
// 					if (params.IS_CD_DIR) {
                    dir(params.CD_DIR) {
                        sh 'mvn clean package'
                    }
// 					} else {
// 						sh 'mvn clean package'
// 					}
				}
            }
        }
        stage('create docker image') {
			steps {
				script {
                    dir(params.CD_DIR) {
                        sh 'docker login --username $DOCKER_CREDS_USR --password $DOCKER_CREDS_PSW'
                        sh ("docker build -t ${params.DOCKER_REPO_ID}/${params.MS_NAME}:latest .")
                    }
				}
			}
		}
		stage('push docker image') {
			steps {
				sh ("docker push  ${params.DOCKER_REPO_ID}/${params.MS_NAME}:${params.DOCKER_TAG}")
			}
        }
// 		stage('create deployment') {
// 			steps {
// 				sh 'kubectl delete deployments ${params.MS_NAME} || true'
// 				sh 'kubectl create -f k8s/deployment.yaml --validate=false'
// 			}
// 		}
// 		stage('create service') {
// 			steps {
// 				sh 'kubectl delete services ${params.MS_NAME} || true'
// 				sh 'kubectl create -f k8s/services.yaml --validate=false'
// 			}
// 		}
    }
}
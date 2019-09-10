pipeline {
  agent any
  stages {
    stage('Env') {
      parallel {
        stage('java') {
          steps {
            sh 'which java'
            sh 'java -version'
          }
        }
        stage('maven') {
          steps {
            sh 'which mvn'
            sh 'mvn -v'
          }
        }
      }
    }
    stage('Build') {
      steps {
        sh 'mvn -DskipTests=true package'
      }
    }
    stage('Delivery') {
      parallel {
        stage('api-gateway-zuul') {
          environment {
            moduleName = 'api-gateway-zuul'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gateway-zuul/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('api-gateway') {
          environment {
            moduleName = 'api-gateway'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gateway/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('config-server-git') {
          environment {
            moduleName = 'config-server-git'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/config-server-git/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('eureka-consumer') {
          environment {
            moduleName = 'eureka-consumer'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/eureka-consumer/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('eureka-producer') {
          environment {
            moduleName = 'eureka-producer'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/eureka-producer/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('eureka-server') {
          environment {
            moduleName = 'eureka-server'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/eureka-server/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('hystrix-dashboard') {
          environment {
            moduleName = 'hystrix-dashboard'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/hystrix-dashboard/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
        stage('turbine') {
          environment {
            moduleName = 'turbine'
          }
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/turbine/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  ${moduleName}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }
            }
          }
        }
      }
    }
    stage('Deploy') {
      steps {
        withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
          sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < /var/lib/jenkins/script/spring-boot.sh start ${deployPath}/api-gateway-1.0.0.jar'
        }
      }
    }
  }
  environment {
    deployPath = '/root/data'
    deployHost = '47.244.175.138'
    projectVersion = '1.0.0'
  }
}
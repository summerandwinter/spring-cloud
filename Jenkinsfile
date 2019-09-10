pipeline {
  agent any
  stages {
    stage('Env') {
      parallel {
        stage('pwd') {
          steps {
            echo 'building...'
            sh 'pwd'
          }
        }
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
        stage('api-gateway-zull') {
          steps {
            sh 'pwd'
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gateway-zuul/target/') {
              sh 'pwd'
              withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
                sh 'scp -i ${privateKey}  api-gateway-zuul-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
              }

            }

          }
        }
        stage('api-gateway') {
          steps {
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gatewayl/target/')
          }
        }
        stage('config-server-git') {
          steps {
            dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gateway-zuul/target/')
          }
        }
      }
    }
    stage('Deploy') {
      steps {
        sh 'ssh -i ${privateKey} ${userName}@${deployHost} "BUILD_ID=dontKillMe nohup java -jar ${deployPath}/api-gateway-zuul-${projectVersion}.jar > /dev/null 2>&1 &"'
      }
    }
  }
  environment {
    deployPath = '/root/data'
    deployHost = '47.244.175.138'
    projectVersion = '0.0.1-SNAPSHOT'
  }
}
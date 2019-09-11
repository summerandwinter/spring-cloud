pipeline {
  agent any
  stages {
    stage('Env') {
      parallel {
        stage('java') {
          steps {
            sh 'printenv'
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
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('api-gateway') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('config-server-git') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('eureka-consumer') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('eureka-producer') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('eureka-server') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('hystrix-dashboard') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
        stage('turbine') {
          steps {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
              sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
              sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
            }
          }
        }
      }
    }
    stage('Deploy') {
      steps {
        withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
          sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${JENKINS_HOME}/script/spring-boot.sh start ${deployPath}/eureka-server-1.0.0.jar'
        }
      }
    }
  }
  environment {
    deployPath = '/root/data'
    deployHost = '47.244.175.138'
    projectVersion = '1.0.0'
    springBootScript = '/var/lib/jenkins/script/spring-boot.sh'
  }
}
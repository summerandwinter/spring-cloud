
def deliverStepNames = ["api-gateway-zuul", "api-gateway", "config-server-git", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]

def deliverSteps = deliverStepNames.collectEntries {
    ["${it}" : transformIntoDeliverStep(it)]
}

def transformIntoDeliverStep(inputString) {
  return {
    node {
      withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'privateKey', usernameVariable: 'userName')]) {
        sh 'ssh -i ${privateKey} ${userName}@${deployHost} "bash -s" < ${springBootScript} stop ${deployPath}/${STAGE_NAME}-${projectVersion}.jar'
        sh 'ssh -i ${privateKey} ${userName}@${deployHost} mv ${deployPath}/${STAGE_NAME}-${projectVersion}.jar ${deployPath}/backup/${STAGE_NAME}-${projectVersion}.jar'
        sh 'scp -i ${privateKey}  ${WORKSPACE}/${STAGE_NAME}/target/${STAGE_NAME}-${projectVersion}.jar ${userName}@${deployHost}:${deployPath}'
      }
    }
  }
}
def environment = ['deployPath=/root/data', 'deployHost=47.244.175.138',  'projectVersion=1.0.0', 'springBootScript=/var/lib/jenkins/script/spring-boot.sh']
node {
  withEnv(environment) {
    stage('Check Env') {
      parallel(
        'jenkins': {
          sh 'printenv'
        },
        'java': {
          sh 'java -version'
        },
        'maven': {
          sh 'mvn -v'
        }
       )
    }
    stage('Build') {
      sh 'mvn -DskipTests=true package'
      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }
    stage('Test') {
      echo 'Testing....'
    }
    stage('Delivery') {
       parallel deliverSteps
    }
    stage('Deploy') {
      echo 'Deploying....'
    }
  }
}
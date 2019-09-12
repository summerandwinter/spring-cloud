
def deliverStepNames = ["api-gateway-zuul", "api-gateway", "config-server-git", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]

def deliverSteps = deliverStepNames.collectEntries {
    ["${it}" : transformIntoDeliverStep(it)]
}

def transformIntoDeliverStep(inputString) {
    return {
        node {
            echo inputString
        }
    }
}

node {
  stage('Environment') {
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
    echo 'Building....'
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
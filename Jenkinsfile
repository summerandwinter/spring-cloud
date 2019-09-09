pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'building...'
        sh 'mvn -DskipTests=true package'
      }
    }
  }
}
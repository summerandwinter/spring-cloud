pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'building...'
        sh 'pwd'
        sh 'which mvn'
        sh 'mvn -v'
        sh 'which java'
        sh 'java -version'
      }
    }
  }
}
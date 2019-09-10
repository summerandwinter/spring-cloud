pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
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
        stage('java') {
          steps {
            sh 'which java'
          }
        }
      }
    }
  }
}
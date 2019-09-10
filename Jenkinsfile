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
    stage('Deploy') {
      steps {
        sh 'pwd'
        dir(path: '/var/lib/jenkins/workspace/spring-cloud_master/api-gateway-zuul/target/') {
          sh 'pwd'
          withCredentials([sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'pem')]) {
            sh 'scp -i ${pem}  api-gateway-zuul-0.0.1-SNAPSHOT.jar root@47.244.175.138:/root/data'
            sh 'ssh -i ${pem} root@47.244.175.138 "nohup java -jar /root/data/api-gateway-zuul-0.0.1-SNAPSHOT.jar &"'
          }
        }

      }
    }
  }
}
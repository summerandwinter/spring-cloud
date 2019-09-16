import groovy.json.JsonSlurper

def environment = ['DEPLOY_PATH=/data/spring-cloud', 'DEPLOY_HOST=47.244.175.138',  'PROJECT_VERSION=1.0.0', 'SPRING_BOOT_SCRIPT=/var/lib/jenkins/script/spring-boot.sh']

def deliverStepNames = ["api-gateway-zuul", "api-gateway", "config-server-git", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]
// def deployStepNames = ["api-gateway-zuul", "api-gateway", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]

// def deliverStepNames = ["config-server-git"]
def deployStepNames = ["eureka-producer", "eureka-server"]


def configServerHealth = false

def deliverSteps = deliverStepNames.collectEntries {
    ["${it}" : transformIntoDeliverStep(it)]
}

def deploySteps = deployStepNames.collectEntries {
    ["${it}" : transformIntoDeployStep(it)]
}

def transformIntoDeliverStep(stepName) {
    return {
        stage(stepName) {
            sh 'salt -G role:slave cmd.script salt://spring-boot.sh "stop ${DEPLOY_PATH}/${STAGE_NAME}.jar"'
            sh 'salt -G role:slave cp.get_file salt://spring-cloud/${STAGE_NAME}-${PROJECT_VERSION}.jar ${DEPLOY_PATH}/${STAGE_NAME}.jar makedirs=True'
        }
    }
}
def transformIntoDeployStep(stepName) {
    return {
        stage(stepName) {
            sh 'salt -G role:slave cmd.script salt://spring-boot.sh "start ${DEPLOY_PATH}/${STAGE_NAME}.jar"'
        }
    }
}
node {

    withEnv(environment) {
        stage('检查环境') {
            parallel(
                    'jenkins': {
                        sh 'printenv'
                        checkout scm
                        echo "current branch: $BRANCH_NAME"
                    },
                    'java': {
                        sh 'java -version'
                    },
                    'maven': {
                        sh 'mvn -v'
                    }
            )
        }
        stage('构建') {
            sh 'printenv'
            sh 'mvn clean'
            sh 'mvn -DskipTests=true package'

        }
        stage('测试') {
            echo 'Testing....'
        }
        stage('归档') {
            echo '拷贝文件到 salt 文件服务目录'
            sh 'cp ${WORKSPACE}/**/target/**.jar /srv/salt/'
            echo '归档文件'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        stage('传输') {
            parallel deliverSteps
        }
        stage('启动配置中心') {
            withCredentials(bindings: [sshUserPrivateKey(credentialsId: 'ffa6fc58-0558-4b74-baeb-b21dd0a035a5', keyFileVariable: 'PRIVATE_KEY', usernameVariable: 'USERNAME')]) {
                echo 'Deploying....'
                sh 'salt -G role:slave cmd.script salt://spring-boot.sh "start ${DEPLOY_PATH}/config-server-git.jar"'
            }
        }
        stage('检查配置中心') {
            def  healthUrl = "http://115.159.195.167:18006/actuator/health"
            def map = null
            def shellStr = null
            try {
                sleep(60)
                shellStr = sh(script: "curl ${healthUrl}", returnStdout: true)
                echo "应用健康检查结果:${shellStr}"
                map = new JsonSlurper().parseText(shellStr)
            } catch (Exception e) {
            }
            if (map != null && "UP" == map.get("status")) {
                echo "应用健康运行"
                configServerHealth = true
            } else {
                sleep(60)//睡眠1分钟
                shellStr = sh(script: "curl ${healthUrl}", returnStdout: true)
                map = new JsonSlurper().parseText(shellStr)
                if (map == null || "UP" != map.get("status")) {
                    throw new RuntimeException("应用不稳定，请检查服务是否正常")
                } else {
                    echo "应用健康运行"
                    configServerHealth = true
                }
            }
        }
        stage('启动服务') {
            if (configServerHealth) {
                parallel deploySteps
            } else {
                echo 'Config service has a problem'
            }
        }
    }
}
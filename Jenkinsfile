import groovy.json.JsonSlurper

def environment = ['DEPLOY_PATH=/data/spring-cloud', 'DEPLOY_HOST=47.244.175.138',  'PROJECT_VERSION=1.0.0', 'SPRING_BOOT_SCRIPT=/var/lib/jenkins/script/spring-boot.sh']

def deliverStepNames = ["api-gateway-zuul", "api-gateway", "config-server-git", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]
// def deployStepNames = ["api-gateway-zuul", "api-gateway", "eureka-consumer", "eureka-producer", "eureka-server", "hystrix-dashboard", "turbine"]

// def deliverStepNames = ["config-server-git"]
def deployStepNames = ["api-gateway",  "eureka-consumer", "eureka-producer"]


def configServerHealth = false
def eurekaServerHealth = false

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
            sh 'salt -G role:slave cmd.script salt://backup.sh "${DEPLOY_PATH} ${STAGE_NAME}.jar"'
            sh 'salt -G role:slave cp.get_file salt://spring-cloud/${STAGE_NAME}-${PROJECT_VERSION}.jar ${DEPLOY_PATH}/${STAGE_NAME}.jar makedirs=True'
        }
    }
}
def transformIntoDeployStep(stepName) {
    return {
        stage(stepName) {
            sh 'salt -G role:slave cmd.script salt://spring-boot.sh "start \'${DEPLOY_PATH}/${STAGE_NAME}.jar --spring.profiles.active=prod\'"'
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
            sh '\\cp ${WORKSPACE}/**/target/**.jar /srv/salt/spring-cloud/'
            echo '归档文件'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        stage('传输') {
            parallel deliverSteps
        }
        stage('启动服务发现') {
            parallel(
                    '0_4': {
                        sh 'salt "VM_0_4_centos" cmd.script salt://spring-boot.sh "start \'${DEPLOY_PATH}/eureka-server.jar --spring.profiles.active=node01\'"'
                    },
                    '0_30': {
                        sh 'salt "VM_0_30_centos"  cmd.script salt://spring-boot.sh "start \'${DEPLOY_PATH}/eureka-server.jar --spring.profiles.active=node03\'"'
                    },
                    '0_103': {
                        sh 'salt "VM_0_103_centos"  cmd.script salt://spring-boot.sh "start \'${DEPLOY_PATH}/eureka-server.jar --spring.profiles.active=node03\'"'
                    }
            )

        }
        stage('检查服务发现') {
            def  healthUrl = "http://115.159.195.167:18000/actuator/health"
            def map = null
            def shellStr = null
            try {
                sleep(10)
                shellStr = sh(script: "curl ${healthUrl}", returnStdout: true)
                echo "应用健康检查结果:${shellStr}"
                map = new JsonSlurper().parseText(shellStr)
            } catch (Exception e) {
            }
            if (map != null && "UP" == map.get("status")) {
                echo "应用健康运行"
                eurekaServerHealth = true
            } else {
                sleep(10)
                shellStr = sh(script: "curl ${healthUrl}", returnStdout: true)
                map = new JsonSlurper().parseText(shellStr)
                if (map == null || "UP" != map.get("status")) {
                    throw new RuntimeException("应用不稳定，请检查服务是否正常")
                } else {
                    echo "应用健康运行"
                    eurekaServerHealth = true
                }
            }
        }
        stage('启动配置中心') {
            if (eurekaServerHealth) {
                sh 'salt -G role:slave cmd.script salt://spring-boot.sh "start \'${DEPLOY_PATH}/config-server-git.jar --spring.profiles.active=prod\'"'
            } else {
                echo '服务发现集群启动失败'
                throw new RuntimeException("应用不稳定，请检查服务是否正常")
            }

        }

        stage('检查配置中心') {
            def  healthUrl = "http://115.159.195.167:18006/actuator/health"
            def map = null
            def shellStr = null
            try {
                sleep(10)
                shellStr = sh(script: "curl ${healthUrl}", returnStdout: true)
                echo "应用健康检查结果:${shellStr}"
                map = new JsonSlurper().parseText(shellStr)
            } catch (Exception e) {
            }
            if (map != null && "UP" == map.get("status")) {
                echo "应用健康运行"
                configServerHealth = true
            } else {
                sleep(10)//睡眠1分钟
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
#!groovy
pipeline {
    //环境变量
    environment {
        //git认证
        __ROPE_GIT_AUTH = "xxxxx"
        //应用名
        APPLICATION_NAME = "${map.APPLICATION_NAME}"
        //应用端口
        SERVER_PORT = "${map.SERVER_PORT}"
        // git地址
        __ROPE_GIT_URL = "${map.GIT_URL}"
    }
  	options{
        buildDiscarder(logRotator(numToKeepStr: '10')) 
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 1, unit: 'HOURS')
    }
    stages {
     stage('Git阶段') {
       checkout([
         $class: 'GitSCM',
         branches: [[name: "${BRANCH_NAME}"]],
         doGenerateSubmoduleConfigurations: false, 
         extensions: [], 
         submoduleCfg: [], 
         userRemoteConfigs: [[credentialsId: "$__ROPE_GIT_AUTH",url: "${__ROPE_GIT_URL}"]]
       ])
     }
     stage('Maven阶段'){
        steps{
          configFileProvider([configFile(fileId: 'maven-global-settings', variable: 'MAVEN_GLOBAL_ENV')]) {
            sh 'mvn -s $MAVEN_GLOBAL_ENV clean package -DskipTests -DskipDocker -U'
          }
        }
     }
    }
}

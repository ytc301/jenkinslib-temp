// vars/generatePipeline.groovy
def call(String lang){    
  if (lang == 'java') {
pipeline {
    agent any
    stages {
        stage('java') {
            steps {

               sh 'echo java'
                }
            }
        }
    }
} else if (lang == 'node'){

pipeline {
    agent any
    stages {
        stage('node') {
            steps {
                sh 'echo node'
            }
        }
     }
   } 
  }
  //其他代码
}

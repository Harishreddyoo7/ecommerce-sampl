pipeline {
  agent any
  environment {
    DEPLOY_HOST = credentials('deploy_host') // optional string credential; or set in job env
    DEPLOY_USER = "${env.DEPLOY_USER}"      // set in job env
    DEPLOY_PATH = "${env.DEPLOY_PATH}"      // set in job env
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }
    stage('Build') {
      steps { sh 'mvn -B -DskipTests clean package' }
    }
    stage('Archive') {
      steps { archiveArtifacts artifacts: 'target/*.war', fingerprint: true }
    }
    stage('Deploy to Tomcat') {
      when { expression { return env.DEPLOY_HOST && env.DEPLOY_USER && env.DEPLOY_PATH } }
      steps {
        sh '''
          WAR=target/ecommerce-sample-war.war
          echo "Deploying $WAR to $DEPLOY_USER@$DEPLOY_HOST:$DEPLOY_PATH"
          scp -o StrictHostKeyChecking=no "$WAR" "$DEPLOY_USER@$DEPLOY_HOST:$DEPLOY_PATH/"
        '''
      }
    }
  }
}

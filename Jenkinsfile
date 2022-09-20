node{
    stage('Checkout'){


        git 'https://github.com/mbenguep/obs.git'
    }
    stage('compile-package'){
        // getting maven home path

        sh "/opt/maven/bin/mvn package"
    }
    stage('Clean Package') {
        echo 'Code Quality'
        withSonarQubeEnv('s1-91') { 
          sh "/opt/maven/bin/mvn clean package"
        }
    }
    stage('SonarQube Analysis') {
        echo 'Code Quality'
        withSonarQubeEnv('s1-91') { 
          sh "/opt/maven/bin/mvn sonar:sonar"
        }
    }
}
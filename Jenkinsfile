node{
    stage('Checkout'){


        git 'https://github.com/mbenguep/obs.git'
    }
    stage('compile-package'){
        // getting maven home path

        sh "/opt/maven/bin/mvn package"
    }
    
    stage('SonarQube Analysis') {
        echo 'Code Quality'
        withSonarQubeEnv('s1-91') { 
          sh "/opt/maven/bin/mvn sonar:sonar"
        }
    }

    stage('Create docker image onto dockerhub') {

        sshPublisher(publishers: [sshPublisherDesc(configName: 'ansible', transfers: [sshTransfer(cleanRemote: false, excludes: '',
         execCommand: '''ansible-playbook obs-image.yml;
         sleep 10''', execTimeout: 1200000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+',
         remoteDirectory: '//opt//docker', 
         remoteDirectorySDF: false, removePrefix: 'target', sourceFiles: 'target/*.jar')], 
         usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
    }

    stage('Copy Artifact to Ansible-Server and deploy'){

        sshPublisher(publishers: [sshPublisherDesc(configName: 'ansible', transfers: [sshTransfer(cleanRemote: false, excludes: '',
        execCommand: '''ansible-playbook -i /opt/docker/inventory /opt/docker/kube_deploy.yml;
        sleep 10;
        ansible-playbook -i inventory kube_service.yml''', execTimeout: 1200000, flatten: false, makeEmptyDirs: false, 
        noDefaultExcludes: false, patternSeparator: '[, ]+', 
        remoteDirectory: '//opt//docker', remoteDirectorySDF: false, removePrefix: 'target', sourceFiles: 'target/*.jar')], 
        usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
            }

}

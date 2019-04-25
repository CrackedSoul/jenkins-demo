pipeline {
    agent none
    stages {
        stage('Package') {
            agent {
                    docker {
                        image 'maven:3-alpine'
                        args '-v /root/.m2:/root/.m2 -v /root/.m2/settings.xml:/usr/share/maven/conf/settings.xml'
                    }
            }
            steps {
                echo 'Package..'
                sh 'mvn clean package -Dmaven.test.skip=true'
                echo 'SonarQube Test..'
             // sh 'mvn sonar:sonar'
            }
        }
        stage('Build') {
        	agent any
            steps {
            	echo 'Clean..'
            	sh 'mv  docker/Dockerfile Dockerfile'
            	sh 'rm -rf docker'
            	sh 'mkdir docker'
            	sh 'mv  Dockerfile docker/Dockerfile'
                echo 'Build..'
                sh 'mv  target/jenkins-demo-0.0.1-SNAPSHOT.jar  docker/jenkins-demo.jar'
                sh "docker build -t jenkins-demo docker/"
            }
        }   
        stage('Deploy') {
        	agent any
            steps {
            	sh 'docker stop jenkins-demo'
                sh "docker run -d --rm --name jenkins-demo -p 8888:8080  jenkins-demo "
            }
        }        
    }
    post{
    	failure{
	    	emailext (
	    		body: """
	    			${JOB_NAME}- Build #${BUILD_NUMBER} Result!</br>
	    			BUILD_URL: <a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a></br>
	    			JOB_URL:<a href=\"$JOB_URL\">$JOB_URL</br>
	    			LogInfo: <a href=\"${env.BUILD_URL}console\">${env.BUILD_URL}console</br>
	    		""", 
	    		recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], 
	    		subject: '${JOB_NAME}- Build #${BUILD_NUMBER} Construction Result',   
	    		to: 'lixucheng@mastercom.cn'
				)
    	}
    }
}
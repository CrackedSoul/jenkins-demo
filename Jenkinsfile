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
                sh 'mvn sonar:sonar'
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
                script{
                	try {
            		 	sh 'docker rmi  jenkins-demo'
			        }
			        catch (exc) {
			            echo '原镜像不存在，直接构建!'
			        }
                }
                sh "docker build -t jenkins-demo docker/"
            }
        }   
        stage('Deploy') {
        	agent any
            steps {
                script{
                	try {
            		 	sh 'docker stop jenkins-demo'
			        }
			        catch (exc) {
			            echo '原镜像不存在，直接构建!'
			        }
                }
                sh "docker run -d --rm --name jenkins-demo -p 8888:8080  jenkins-demo "
            }
        }        
    }
    post{
    	failure{
            emailext (
        	    attachLog: true,
                body:'${SCRIPT, template="email.template"}',
        	    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
        	    subject: '${JOB_NAME}- Build #${BUILD_NUMBER} Construction Result',
        	    to: 'huangyulong@mastercom.cn'
        	)
        }
    }
}
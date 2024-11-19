def  call(String [] params) { 
	env = params[0]
     def ANT_HOME = "/opt/apache-ant-1.9.15/bin" 
			 	if (env=="dev"){
				echo "Sonar has been started"
				withSonarQubeEnv('SonarQube') {               
				sh "${ANT_HOME}/ant -Dpipe=${BUILD_TAG} sonar_dev"
						    } 
						}else if (env=="test"){	
				echo "Sonar has been started"
				withSonarQubeEnv('SonarQube') {               
				sh "${ANT_HOME}/ant -Dpipe=${BUILD_TAG} sonar_test"
						    }
						}else if (env=="staging"){
				echo "Sonar has been started"
				withSonarQubeEnv('SonarQube') {               
				sh "${ANT_HOME}/ant -Dpipe=${BUILD_TAG} sonar_staging"
						    }
						} else {
				echo "Wrong value passed"
						}
}
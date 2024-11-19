def call(String[] params){   
	echo "Starting Test"
			  withGradle {
                sh './gradlew test'
            }
			}	

def call(String[] params){
	echo "Building with Gradle"
		withGradle {
                 sh 'chmod 777 ./gradlew'
                sh './gradlew clean bootJar -x test'
            }
		}
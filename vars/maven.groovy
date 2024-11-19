#!/usr/bin/checkout groovy
def call(String[] params){ 
	try{
	echo "Building with Maven"
	withMaven(maven: 'maven-3') {
                sh "mvn clean install -DskipTests"
				}
            }catch(Exception e) {
			mail('Maven Build Failed')
			}
            }

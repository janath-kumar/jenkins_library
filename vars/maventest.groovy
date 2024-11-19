#!/usr/bin/checkout groovy
def call(String[] params){ 
	try{
	echo "Starting Maven Test"
	withMaven(maven: 'maven-3') {
                sh "mvn test"
               	 junit testResults: 'target/surefire-reports/emailable-report.html',
                 skipPublishingChecks: true,
				 allowEmptyResults: true
				}
            }catch(Exception e) {
			mail("Maven Test Failed")
			}
            }

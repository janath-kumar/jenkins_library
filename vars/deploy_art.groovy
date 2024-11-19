def call (String [] params) {
env = params[0]
def ANT_HOME = "/opt/apache-ant-1.9.15/bin"
try {
	if (env=="dev"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
        try{
		sh "ant -buildfile build.xml dev-stop"
        sh "ant -buildfile build.xml dev-backup"
		sh "ant -buildfile build.xml dev-start"
		} catch (Exception e) {
		sh "ant -buildfile build.xml dev-restore"
        sh "ant -buildfile build.xml dev-start"
		}
	}		
	} else if (env=="test"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
   try {    
		sh "ant -buildfile build.xml test-stop"
		sh "ant -buildfile build.xml test-backup"
		sh "ant -buildfile build.xml test-start"
	} catch (Exception e) {
		sh "ant -buildfile build.xml test-restore"
        sh "ant -buildfile build.xml test-start"
		}
	}			
	}else if (env=="staging"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
	try{
		sh "ant -buildfile build.xml staging-stop"
		sh "ant -buildfile build.xml staging-backup"
		sh "ant -buildfile build.xml staging-start"
        } catch (Exception e) {
		sh "ant -buildfile build.xml staging-restore"
        sh "ant -buildfile build.xml staging-start"
	}
	}
	} else {
	echo "Wrong value passed"
		}
    }	
		 catch (Exception e) {
			echo " Deployement failed"
		}
	
}       
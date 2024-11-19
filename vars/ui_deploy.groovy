def call (String [] params) {
env = params[0]
def ANT_HOME = "/opt/apache-ant-1.9.15/bin"
try {
	if (env=="dev"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
		sh "ant -buildfile build.xml deployroot_dev"
	}		
	} else if (env=="test"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
		sh "ant -buildfile build.xml deployroot_test"
	}			
	}else if (env=="staging"){
withEnv(["PATH+ANT=${tool 'ANT 1.9'}/bin"]) { 
		sh "ant -buildfile build.xml staging"
	}
	} else {
	echo "Wrong value passed"
		}
		} catch (Exception e) {
			mail("Ui Deployemnt failed")
		}
	}
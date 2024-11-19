def call (String[] params) {
  String dep_cred = params [0]
  String dep_host = params [1]
  int port = Integer.parseInt(params [2]) 
  String dep_stop = params [3]
  String dep_backup = params [4]
  String dep_artifact = params [5]
  String dep_path = params [6]
  String dep_start = params [7]
  String dep_restore = params [8]
  echo    "port= " + port   
    def isDeployed = false
    try {
				withCredentials(
                        [usernamePassword(
                            credentialsId: "${dep_cred}",
                            usernameVariable: 'USERNAME',
                            passwordVariable: 'PASSWORD')]) {
                    def remoteCred = [
                        name: "${dep_cred}",
                        host: "${dep_host}",
                        port: port, 
                        user: USERNAME,
                        password: PASSWORD,
                        allowAnyHosts: true
                    ]
                    echo 'stoping old running artifact.'
                    sshCommand remote: remoteCred, command: "${dep_stop}"
                    echo 'backing up artifact before deploying.'
                    sshCommand remote: remoteCred, command: "${dep_backup}"
                    echo 'deploying artifact...'
					echo "${dep_artifact}"
                    sshPut(remote: remoteCred,
                            from: "${dep_artifact}",
							into: "${dep_path}",
                            failOnError: true)
                    isDeployed = true
                    echo 'starting newly deployed artifact...'
                    sshCommand remote: remoteCred, command: "${dep_start}"
                             }
            }catch (Exception e) {
            if (isDeployed) {
                withCredentials(
                        [usernamePassword(
                            credentialsId: "${dep_cred}",
                            usernameVariable: 'USERNAME',
                            passwordVariable: 'PASSWORD')]) {
                    def remoteCred = [
                        name: "${dep_cred}",
                        host: "${dep_host}",
                        port: port,
                        user: USERNAME,
                        password: PASSWORD,
                        allowAnyHosts: true
                    ]
                    echo 'restoring artifact.'
                    sshCommand remote: remoteCred, command: "${dep_restore}"
            	}
		}
    } 
}
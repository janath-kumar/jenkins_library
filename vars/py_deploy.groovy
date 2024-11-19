def call (String[] params){
String dep_cred = params [0]
  String dep_host = params [1]
  int port = Integer.parseInt(params [2]) 
  String dep_stop = params [3]
  String dep_backup = params [4]
  String dep_artifact = params [5]
  String dep_artifact_pip = params [6]
  String dep_path = params [7]
  String dep_path_pip = params [8]
  String dep_start = params [9]
  String dep_restore = params [10]
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
                    sshCommand(remote: remoteCred,
                           command: "${dep_stop}",
                            failOnError: true)
                            
                    echo 'backing up artifact before deploying.'
                    sshCommand(remote: remoteCred,
                            command: "${dep_backup}",
                            failOnError: true)
    
                    echo 'deploying artifact...  ${dep_artifact}'
                    sh "pwd"
                    sshPut(remote: remoteCred,
                            from: "${dep_artifact}",
                            filterRegex: /.class/,
                            into: "${dep_path}",
                            failOnError: true)
                    sshPut(remote: remoteCred,
                            from: "${dep_artifact_pip}",
                            into: "${dep_path_pip}",
                            failOnError: true)
                    isDeployed = true
                    echo 'starting newly deployed artifact...'
                    sshCommand(remote: remoteCred,
                            command: "${dep_start}",
                            failOnError: true)
                }
        } catch (Exception e) {
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
                    sshCommand(remote: remoteCred,
                            command: "${dep_restore}",
                            failOnError: true)
                }
	        }
	//		mail('Python Deployment failed')
        }
	}
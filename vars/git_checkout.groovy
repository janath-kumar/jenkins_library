#!/usr/bin/checkout groovy
package xerago.devops
def call (String[] params) {
    String branch_name = params[0]
    String git_cred =  params[1] 
    String git_url = params[2]
    String git_sparse_path= params[3]
    def scmVars
	scmVars = checkout(scm: [
            $class: 'GitSCM',
            userRemoteConfigs: [
                [url: "${git_url}", credentialsId: "${git_cred}"]
            ],
            branches: [
           [name: "${branch_name}"]
            ],
            browser: [
                $class: 'GitWeb',
                repoUrl: "${git_url}"
            ],
            extensions: [
                [$class: 'SparseCheckoutPaths', sparseCheckoutPaths: [[path: "${git_sparse_path}"]]],
                [$class: 'CloneOption', shallow: true, depth: 5, honorRefspec: true]
            ],
            changelog: true,
           poll: true
        ])	
	}
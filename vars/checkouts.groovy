package xerago.devops
def call (String[] params) {
    String branch_name = params[0]
    String git_cred =  params[1] 
    String git_url = params[2]
git branch: "${branch_name}", credentialsId: "${git_cred}", url: "${git_url}"
}
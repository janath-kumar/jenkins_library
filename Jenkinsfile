@Library('my-shared-library') _
 import groovy.json.*
 import groovy.json.JsonSlurperClassic
 import org.jenkinsci.plugins.workflow.job.*
 import groovy.transform.Field
node {
def resultJSON = readJSON file: 'config_master.json', String: ''
  for (int i = 0; i<resultJSON.project.pipelines.size(); i++) {
        sh '''
        pwd
        cp config_master.json /tmp
        rm -rf *
        cp /tmp/config_master.json $PWD
        ls
        '''
        def artifact = resultJSON.project.pipelines[i]
        String language_template =resultJSON.project.pipelines[i].language_template
        l= language_template.length()
        project_config=[
            "branch_name": resultJSON.project.pipelines[i].branch_name,
            "git_cred": resultJSON.project.git.git_cred,
            "repo_url": resultJSON.project.git.repo_url,
            "git_sparse_path": resultJSON.project.pipelines[i].git_sparse_path, 
            "dep_cred": resultJSON.project.pipelines[i].ssh.dep_cred, 
            "dep_host": resultJSON.project.pipelines[i].ssh.dep_host,
            "port":     resultJSON.project.pipelines[i].ssh.port,
            "dep_stop": resultJSON.project.pipelines[i].dep_stop_script,
            "dep_backup": resultJSON.project.pipelines[i].dep_backup_script,
            "dep_artifact": resultJSON.project.pipelines[i].dep_artifact,
            "dep_artifact_pip": resultJSON.project.pipelines[i].dep_artifact_pip,
            "dep_path":  resultJSON.project.pipelines[i].dep_path,
            "dep_path_pip": resultJSON.project.pipelines[i].dep_path_pip,
            "dep_start": resultJSON.project.pipelines[i].dep_start_script,
            "dep_restore": resultJSON.project.pipelines[i].dep_restore_script,
            "env": resultJSON.project.pipelines[i].env
            ]
        if (language_template.length()>0){
            String json_file = language_template 
            String full_json_file ='/data/jenkins/workspace/language_templates/'+json_file 
            def resultTemplateJSON = readJSON file: full_json_file, String: '' 
                     for (int j=0; j< resultTemplateJSON.stages.size(); j++){
            def stage_list = resultTemplateJSON.stages[j].stage_name
            if (stage_list){    
                stage(stage_list){                        
                    //handle lang template logic
                    //read out json 
                for (int k=0; k< resultTemplateJSON.stages[j].actions.size(); k++){                                                                
                            action_name = resultTemplateJSON.stages[j].actions[k].name 
                        src_params = resultTemplateJSON.stages[j].actions[k].params
                            //Processing action parameters dynamically
                            if(src_params && src_params.size()>0){
                                def dest_params = new String[src_params.size()]                                
                                for(int l=0; l<src_params.size(); l++){                                    
                                    dest_params[l] = project_config."${src_params[l]}"                                    
                                }
                               def ch_dir = resultTemplateJSON.stages[j].actions[k].ch_dir
                                if (ch_dir){
                                 dir("${resultJSON.project.pipelines[i].git_sparse_path}".substring(1)){ 
                                 "${action_name}" (dest_params) 
                                    }
                                }else{
                                "${action_name}"(dest_params)
                                }
                                }
                            else {
                              dir("${resultJSON.project.pipelines[i].git_sparse_path}".substring(1)){
                                "${action_name}"()
        }           
       }
      }
     }
    }
   }
  }
 }
}
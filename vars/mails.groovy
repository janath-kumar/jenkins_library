#!/usr/bin/env groovy 
def call(String[] params){
String DEFAULT_CONTENT = params[0]
emailext(body: '${DEFAULT_CONTENT}', mimeType: 'text/html',
                    replyTo: '$DEFAULT_REPLYTO', subject: 'Stage Failed',
                    to: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
                        [$class: 'RequesterRecipientProvider']])) 
						}
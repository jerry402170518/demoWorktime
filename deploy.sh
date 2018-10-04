readonly WAR_FILE_NAME=demoWorktime.war
readonly WAR_DEPLOY_PATH=/usr/local/tomcat/webapps
readonly HOST=db.yanru.me
sftp -o StrictHostKeyChecking=no root@$HOST -b <<EOF
put $WAR_FILE_NAME
EOF
ssh -o StrictHostKeyChecking=no root@$HOST docker cp $WAR_FILE_NAME tomcat:$WAR_DEPLOY_PATH/$WAR_FILE_NAME

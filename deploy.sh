sftp -o StrictHostKeyChecking=no root@yanru.me -b <<EOF
put demoWorktime.war /opt/tomcat/webapps
EOF
ssh -o StrictHostKeyChecking=no root@yanru.me service tomcat restart

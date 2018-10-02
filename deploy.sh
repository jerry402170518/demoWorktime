sftp root@yanru.me -b <<EOF
put demoWorktime.war /opt/tomcat/webapps
EOF
ssh root@yanru.me service tomcat restart

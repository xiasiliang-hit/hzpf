
mvn clean package
scp /Users/danny/work/hzpf/xdpf-web/target/xdpf.war root@139.129.58.215:~
ssh root@139.129.58.215 unzip -o /root/xdpf.war -d /home/admin/xdpf/xdpf.war/
#ssh root@139.129.58.21 bash /usr/local/tomcat/bin/shutdown.sh
#ssh root@139.129.58.21 bash /usr/local/tomcat/bin/startup.sh
mvn  package
#scp /Users/danny/work/hzpf/xdpf-web/target/xdpf.war root@139.129.58.215:~
cp ./xdpf-web/target/xdpf.war /root
#ssh root@139.129.58.215
unzip -o /root/xdpf.war -d /home/admin/xdpf/xdpf.war/
bash /root/redeploy.sh 
#ssh root@139.129.58.21 bash /usr/local/tomcat/bin/startup.sh

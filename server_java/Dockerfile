FROM develar/java:latest
MAINTAINER German Blanco

ADD files/mysql-connector-java-5.0.8-bin.jar /root/mysql-connector-java-5.0.8-bin.jar
ADD files/dummy-web-server.jar /root/dummy-web-server.jar
ADD files/start.sh /root/start.sh

RUN chmod +x /root/start.sh

ENTRYPOINT ["/root/start.sh"]

FROM mysql/mysql-server:latest
MAINTAINER German Blanco

ADD files/start.sh /root/start.sh

RUN chmod +x /root/start.sh

ENTRYPOINT ["/root/start.sh"]

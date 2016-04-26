FROM alpine:latest
MAINTAINER German Blanco

RUN apk add --update python musl-dev python-dev gcc mysql-client mariadb-dev py-pip && \
    pip install MySQL-python

ADD files/dummy-web-server.py /root/dummy-web-server.py
ADD files/start.sh /root/start.sh

RUN chmod +x /root/dummy-web-server.py
RUN chmod +x /root/start.sh

ENTRYPOINT ["/root/start.sh"]

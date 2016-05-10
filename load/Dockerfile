FROM alpine:latest
MAINTAINER German Blanco
RUN apk add --update mysql-client erlang erlang-inets erlang-ssl erlang-os-mon gcc make bash && \
    apk add --update erlang-crypto erlang-xmerl erlang-public-key erlang-dev erlang-parsetools && \
    rm -rf /var/cache/apk/* && \
    mkdir /src && \
    cd /src && \
    wget -qO- http://tsung.erlang-projects.org/dist/tsung-1.6.0.tar.gz | tar xz && \
    cd tsung-1.6.0 && \
    ./configure && \
    make && \
    make install && \
    mkdir /root/.tsung

ADD files/tsung.xml /root/.tsung/tsung.xml
ADD files/start.sh /root/start.sh

RUN chmod +x /root/start.sh

ENTRYPOINT ["/root/start.sh"]

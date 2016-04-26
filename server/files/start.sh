#!/bin/sh

if [ ! -z ${THEDB+x} ]; then sed -i.bak s/reto1db/$THEDB/ /root/dummy-web-server.py ; fi

python /root/dummy-web-server.py

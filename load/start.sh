#!/bin/sh
if [ -z ${THEDB+x} ]; then 
  sudo docker run --link reto1server --name reto1load reto1load
else
  sudo docker run -e "THESERVER=$THESERVER" -p 80 --name reto1server -d reto1server
fi

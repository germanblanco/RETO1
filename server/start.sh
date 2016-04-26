#!/bin/sh
if [ -z ${THEDB+x} ]; then 
  sudo docker run --link reto1db -p 80 --name reto1server -d reto1server
else
  sudo docker run -e "THEDB=$THEDB" -p 80 --name reto1server -d reto1server
fi

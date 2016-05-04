#!/bin/sh

THEDB=${THEDB:-reto1db}

java -cp /root/mysql-connector-java-5.0.8-bin.jar:/root/dummy-web-server.jar com.happylife.demo.Main $THEDB

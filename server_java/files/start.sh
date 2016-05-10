#!/bin/sh

THEDB=${THEDB:-reto1db}

java -jar /root/dummy-web-server.jar | java -cp /root/mysql-connector-java-5.0.8-bin.jar:/root/dummy-web-server.jar com.happylife.demo.InsertThread $THEDB

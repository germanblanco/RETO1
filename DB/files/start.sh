#!/bin/bash

/entrypoint.sh mysqld &
sleep 60
mysql -h$HOSTNAME -uroot -ppasswd --execute="CREATE DATABASE reto1;"
mysql -h$HOSTNAME -uroot -ppasswd reto1 --execute="CREATE TABLE reto1 (value VARCHAR(100) NOT NULL, insert_time TIMESTAMP NOT NULL);"

wait %1

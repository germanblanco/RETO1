#!/bin/sh

mkdir -p classes && \
javac -sourcepath src -d classes src/com/happylife/demo/*.java && \
cd classes && \
jar -cfe ../files/dummy-web-server.jar com.happylife.demo.Main * && \
cd .. && \
sudo docker build -t reto1server .

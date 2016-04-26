#!/bin/sh

if [ ! -z ${THESERVER+x} ]; then sed -i.bak s/reto1server/$THESERVER/ /root/.tsung/tsung.xml ; fi

tsung start

#! /bin/bash

set -e

gradle clean test shadowJar

sudo mkdir -p /etc/keyper/

sudo chown $USER:wheel /etc/keyper

cp build/libs/keyper-1.0.0.jar /etc/keyper/keyper-1.0.0.jar
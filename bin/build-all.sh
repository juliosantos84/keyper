#! /bin/bash

BIN_DIR=$(dirname $0)

$BIN_DIR/build-java.sh
$BIN_DIR/build-container.sh

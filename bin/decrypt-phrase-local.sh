#! /bin/bash

export AWS_DEFAULT_REGION=$1
export AWS_REGION=$1
export AWS_ACCESS_KEY_ID=$2
export AWS_SECRET_ACCESS_KEY=$3
export AWS_KMS_KEY_ARN=$4
export KEYPER_PHRASE_SIZE=24
export KEYPER_SECRET_FILE=/tmp/secret/keyper-secret

java -cp /etc/keyper/keyper-1.0.0.jar com.everythingbiig.keyper.Keyper -d
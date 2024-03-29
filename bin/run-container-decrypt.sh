#! /bin/bash

AWS_DEFAULT_REGION=$1
AWS_REGION=$1
AWS_ACCESS_KEY_ID=$2
AWS_SECRET_ACCESS_KEY=$3
AWS_KMS_KEY_ARN=$4
KEYPER_PHRASE_SIZE=24

docker run -it --rm -e AWS_DEFAULT_REGION=${AWS_DEFAULT_REGION} \
    -v /tmp/secret:/tmp/secret \
    -e AWS_REGION=${AWS_REGION} \
    -e AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID} \
    -e AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY} \
    -e AWS_KMS_KEY_ARN=${AWS_KMS_KEY_ARN} \
    -e KEYPER_PHRASE_SIZE=${KEYPER_PHRASE_SIZE} \
    -e KEYPER_SECRET_FILE=/tmp/secret/keyper-secret \
    keyper:1.0.0 java -cp /etc/keyper/keyper-1.0.0.jar com.everythingbiig.keyper.Keyper -d
#! /bin/bash

eval "$(cat ./tmp/KmsUser${1})"
aws iam detach-user-policy --user-name ${KEYPER_USER_NAME} --policy-arn ${KEYPER_POLICY_ARN}
aws iam delete-user --user-name ${KEYPER_USER_NAME}
aws iam delete-policy --policy-arn ${KEYPER_POLICY_ARN}
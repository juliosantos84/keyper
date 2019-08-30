#! /bin/bash

STAGE=$1
KEYPER_POLICY_NAME="KeyperKmsPolicy${STAGE}"
KEYPER_USER_NAME="KeyperUser${STAGE}"
KMS_KEY_ARN=$2
KMS_KEY_ALIAS_ARN=$3

mkdir -p ./tmp

cat << POLICY > ./tmp/policy.json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "kms:GetParametersForImport",
                "kms:Decrypt",
                "kms:ListKeyPolicies",
                "kms:ListRetirableGrants",
                "kms:Encrypt",
                "kms:GetKeyRotationStatus",
                "kms:GetKeyPolicy",
                "kms:ReEncryptTo",
                "kms:DescribeKey",
                "kms:ListResourceTags",
                "kms:ReEncryptFrom",
                "kms:ListGrants"
            ],
            "Resource": [
                "${KMS_KEY_ARN}",
                "${KMS_KEY_ALIAS_ARN}"
            ]
        },
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": [
                "kms:DescribeCustomKeyStores",
                "kms:ListKeys",
                "kms:ListAliases"
            ],
            "Resource": "*"
        }
    ]
}

POLICY

KEYPER_POLICY_ARN=$(aws iam create-policy --policy-name $KEYPER_POLICY_NAME \
    --policy-document file://tmp/policy.json \
    | jq -r '.Policy.Arn')
aws iam create-user --user-name $KEYPER_USER_NAME
aws iam attach-user-policy --user-name $KEYPER_USER_NAME --policy-arn $KEYPER_POLICY_ARN
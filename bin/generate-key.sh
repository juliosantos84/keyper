#! /bin/bash

KEY_STAGE=$1
KEY_NAME="keyper-${KEY_STAGE}"
KEY_DESCRIPTION="Keyper ${KEY_STAGE} encryption key"
ALIAS_NAME="alias/${KEY_NAME}"
KEY_FILE="./tmp/KmsKey${KEY_STAGE}"

mkdir -p "./tmp"

echo "Creating key ${KEY_NAME}"

# Create the key and grab the ARN
KEY_ARN=$(aws kms create-key --description "${KEY_DESCRIPTION}" --key-usage ENCRYPT_DECRYPT \
    --origin AWS_KMS \
    --tags TagKey=Name,TagValue=${KEY_NAME} \
    | jq -r .KeyMetadata.Arn)

echo "Creating alias for ${KEY_NAME} (${KEY_ARN})"

# Wait for the key to be available before creating the alias
sleep 10

# Create the alias
aws kms create-alias --alias-name "${ALIAS_NAME}" --target-key-id "${KEY_ARN}"

ALIAS_ARN=$(aws kms list-aliases | jq -r ".Aliases[] | select(.AliasName == \"${ALIAS_NAME}\") | .AliasArn")

echo "Key Arn: ${KEY_ARN}\nKey Alias Arn: ${ALIAS_ARN}"

echo "Saving key info to ${KEY_FILE}"
echo -e "AWS_KMS_KEY_ARN=${KEY_ARN}\nAWS_KMS_KEY_ALIAS_ARN=${ALIAS_ARN}" > ${KEY_FILE}
echo "Adding key to gradle.properties"
echo "AWS_KMS_KEY_ARN=${ALIAS_ARN}" > gradle.properties
echo "Done."
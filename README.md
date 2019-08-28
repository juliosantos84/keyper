# keyper

A simple utility to encrypt mnemonic phrases.

## building

1. Run `gradle build shadowJar` to generate a fat jar.
2. Run `build-container.sh` to create a container to run the utility in.  This is optional to create a sandbox environment in an isolated network.

## setup

1. Run `bin/generate-key.sh <KEY_STAGE>` to create an AWS KMS key with the name `keyper-<KEY_STAGE>`.  Note the arns provided.
2. Create an IAM user with permissions to use the key created.
3. Create an access key for the user.

## usage

Run `bin/capture-phrase-container.sh us-east-1 <SECRET_KEY_ID> <SECRET_ACCESS_KEY> <KMS_KEY_ARN>`

This will place the encrypted mnemonic phrase in `/tmp/secret/keyper-secret` - this location can be overridden in `bin/capture-phrase-container.sh`.

Save this file somewhere safe, maybe S3 or a personal USB stick.

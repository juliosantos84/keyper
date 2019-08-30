# keyper

A simple utility to keep your keys secret.  `keyper`, get it?  

Supports capturing BIP39 mnemonic phrases with word validation, and encrypting to file using AWS KMS.

## requirements
- An AWS account
- awscli 1.16+ (tested with 1.16.70)
- java 1.8

## building

1. Run `bin/build-all.sh`.

If you don't want to use the container, you can run `bin/build-java.sh` instead.

## setup

1. Run `bin/download-wordlist.sh` to get the wordlist.
2. Run `bin/generate-key.sh <KEY_STAGE>` to create an AWS KMS key with the name `keyper-<KEY_STAGE>`.
The script will write the keys to `./KmsKey<KEYSTAGE>.txt` and will also update `gradle.properties` so it can be used in unit tests.
3. Run `bin/create-iam-user.sh <USER_STAGE> <KEY_ARN> <KEY_ALIAS_ARN>` to create a user with access to the Kms key.
4. Run `aws iam create-access-key --user-name KeyperUser<USER_STAGE>` to create an access key for the user.

## usage

You can run this on your local environment, or in a container.

Before starting configure the 
### capture & encrypt

Captures a mnemonic phrase and encrypts it to a file

1. Run `bin/run-container-capture.sh <REGION> <SECRET_KEY_ID> <SECRET_ACCESS_KEY> <KMS_KEY_ARN>`

This will place the encrypted mnemonic phrase in `/tmp/secret/keyper-secret` - this location can be overridden in `bin/capture-phrase-container.sh`.

2. Save this file somewhere safe, maybe S3 or a personal USB stick.

### decrypt

Decrypts a file to display the mnemonic phrase

1. Run `bin/run-container-decrypt.sh <REGION> <SECRET_KEY_ID> <SECRET_ACCESS_KEY>  <KMS_KEY_ARN>`

This will read the encrypted file in `/tmp/secret/keyper-secret`.

2. Save the screen output
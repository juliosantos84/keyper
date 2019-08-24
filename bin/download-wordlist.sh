#! /bin/bash

set -e

ROOT_DIR=$(dirname $0)
RESOURCES_DIR=${ROOT_DIR}/../src/resources
WORDLIST_URL="https://raw.githubusercontent.com/bitcoin/bips/master/bip-0039/english.txt"
WORDLIST_PATH=$RESOURCES_DIR/wordlist.txt

if [[ ! -e "$RESOURCES_DIR" ]]; then
    echo "Creating $RESOURCES_DIR"
    mkdir -p $RESOURCES_DIR
fi

echo "Downloading $WORDLIST_URL to $WORDLIST_PATH"
curl -s $WORDLIST_URL -o $WORDLIST_PATH

if [[ ! -e "$WORDLIST_PATH" ]]; then
    echo "Unable to download wordlist"
    exit -1;
fi
#! /bin/bash

KEY_ARN="arn:aws:kms:us-east-1:297473205123:key/b59fae51-3b3b-4d98-a41d-45d8deb1b522"

aws kms schedule-key-deletion --key-id ${KEY_ARN} --pending-window-in-days 7
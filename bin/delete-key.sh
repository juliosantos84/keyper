#! /bin/bash

KEY_ARN=

aws kms schedule-key-deletion --key-id ${KEY_ARN} --pending-window-in-days 7
#!/bin/sh
SNAPCRAFT_CREDENTIALS_KEY=password
openssl des-ede3-ofb -d -in .circleci/credentials.enc -out credentials -k $SNAPCRAFT_CREDENTIALS_KEY -v


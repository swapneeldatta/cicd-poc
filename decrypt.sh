#!/bin/sh
SNAPCRAFT_CREDENTIALS_KEY=password
openssl -aes-256-cfb8 -d -bufsize 8 -in .circleci/credentials.enc -out credentials -k $SNAPCRAFT_CREDENTIALS_KEY -md sha1


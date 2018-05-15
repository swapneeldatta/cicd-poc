#!/bin/sh

# Enter your Ubuntu One e-mail address and password. Required for Launchpad to
# connect to git 4 times a day to check for updates.
# snapcraft export-login credentials

# Encrypt the credential files
openssl enc -aes-256-cfb8 -bufsize 8 -e -in credentials -out .circleci/credentials.enc -md sha1

# Decrypt
# openssl -aes-256-cfb8 -d -bufsize 8 -in .circleci/credentials.enc -out credentials -k $SNAPCRAFT_CREDENTIALS_KEY -md sha1

# Cleanup!
#rm creds

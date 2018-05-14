#!/bin/sh

# Enter your Ubuntu One e-mail address and password. Required for Launchpad to
# connect to git 4 times a day to check for updates.
snapcraft export-login credentials

# Encrypt the credential files
openssl des-ede3-ofb -bufsize 8 -e -in credentials -out .circleci/credentials.enc

# Cleanup!
#rm creds

#!/bin/sh

# Enter your Ubuntu One e-mail address and password. Required for Launchpad to
# connect to git 4 times a day to check for updates.
snapcraft export-login creds

# Encrypt the credential files
openssl aes-256-cbc -e -in creds -out .circleci/credentials.enc

# Cleanup!
rm creds

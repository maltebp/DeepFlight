#!/usr/bin/env bash

if [ "$1" != "-d" ]; then
    mvn clean package
else
    printf "Skipping Maven Build\n"
fi

# Create target directory
ssh -i "$AMAZON_KEY" df-user@maltebp.dk "mkdir -p ./DeepFlight/UserAPI" &&\
scp -r -i "$AMAZON_KEY" Dockerfile startContainer.sh target/UserAPI.jar df-user@maltebp.dk:./DeepFlight/UserAPI &&\
ssh -i "$AMAZON_KEY" df-user@maltebp.dk "cd ./DeepFlight/UserAPI && ./startContainer.sh"
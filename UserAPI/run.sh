#!/usr/bin/env bash

USER=df-user

# Check if Maven build should be skipped
if [[ $* == *-d* ]]
then
    printf "Skipping Maven Build\n"
else
    mvn clean package
fi

# Create target directory
ssh -i "$DF_SERVER_KEY" "$USER"@maltebp.dk "mkdir -p ./DeepFlight/UserAPI" &&\
scp -r -i "$DF_SERVER_KEY" Dockerfile startContainer.sh target/UserAPI.jar "$USER"@maltebp.dk:./DeepFlight/UserAPI &&\
ssh -i "$DF_SERVER_KEY" "$USER"@maltebp.dk "cd ./DeepFlight/UserAPI && ./startContainer.sh"
#!/usr/bin/env bash

mvn clean package &&\

ssh -i "$DF_SERVER_KEY" df-user@maltebp.dk "mkdir -p ./DeepFlight/GameAPI" &&\
scp -r -i "$DF_SERVER_KEY" Dockerfile target/GameAPI.jar startContainer.sh df-user@maltebp.dk:./DeepFlight/GameAPI &&\
ssh -i "$DF_SERVER_KEY" df-user@maltebp.dk "cd DeepFlight/GameAPI &&  ./startContainer.sh"
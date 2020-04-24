#!/usr/bin/env bash

#mvn clean package\

mvn clean package &&\
scp -r -i "$AMAZON_KEY" Dockerfile target/GameAPI.jar ec2-user@maltebp.dk:./DeepFlight/GameAPI &&\

ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd DeepFlight/GameAPI && docker build -t deepflight/gameapi . && docker run -d -p 10000:10000 deepflight/gameapi"

#scp -r -i "$AMAZON_KEY" pom.xml Dockerfile src ec2-user@maltebp.dk:./DeepFlight/GameAPI
#scp -r -i "$AMAZON_KEY" src/main/java/* ec2-user@maltebp.dk:./DeepFlight/GameAPI/

# && ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd /home/ec2-user/HangmanOnline && ./dbapi_start.sh"
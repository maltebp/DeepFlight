#!/usr/bin/env bash

#mvn clean package\

target=./DeepFlight/UserAPI

#mvn clean package &&\
scp -r -i "$AMAZON_KEY" Dockerfile target/UserAPI.jar ec2-user@maltebp.dk:./DeepFlight/UserAPI &&\
ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd ./DeepFlight/UserAPI && docker build -t deepflight/userapi . && docker run -d -p 7000:7000 deepflight/userapi"

#scp -r -i "$AMAZON_KEY" pom.xml Dockerfile src ec2-user@maltebp.dk:./DeepFlight/GameAPI
#scp -r -i "$AMAZON_KEY" src/main/java/* ec2-user@maltebp.dk:./DeepFlight/GameAPI/

# && ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd /home/ec2-user/HangmanOnline && ./dbapi_start.sh"
#!/usr/bin/env bash

#mvn clean package\

mvn clean package &&\
scp -r -i "$AMAZON_KEY" Dockerfile target/GameAPI.jar startContainer.sh ec2-user@maltebp.dk:./DeepFlight/GameAPI &&\

ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd DeepFlight/server.GameAPI && ./startContainer.sh"

#scp -r -i "$AMAZON_KEY" pom.xml Dockerfile src ec2-user@maltebp.dk:./DeepFlight/server.GameAPI
#scp -r -i "$AMAZON_KEY" src/main/java/* ec2-user@maltebp.dk:./DeepFlight/server.GameAPI/

# && ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd /home/ec2-user/HangmanOnline && ./dbapi_start.sh"
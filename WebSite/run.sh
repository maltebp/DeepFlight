#!/usr/bin/env bash


printf "Depoying Website to server\n"
scp -q -r -i "$AMAZON_KEY" ../WebSite ec2-user@maltebp.dk:./DeepFlight
ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd DeepFlight/WebSite && ./startContainer.sh"

#scp -r -i "$AMAZON_KEY" pom.xml Dockerfile src ec2-user@maltebp.dk:./DeepFlight/GameAPI
#scp -r -i "$AMAZON_KEY" src/main/java/* ec2-user@maltebp.dk:./DeepFlight/GameAPI/

# && ssh -i "$AMAZON_KEY" ec2-user@maltebp.dk "cd /home/ec2-user/HangmanOnline && ./dbapi_start.sh"
#!/bin/bash
# Script to build a docker image and deploy in a container

imageName="deepflight/gameapi"
containerName="df_gameapi"

printf "\nCreating image '$imageName'\n"
docker build -t "$imageName" .
# Check if build succeded ($? is result of last command)
if [ $? -ne 0 ]; then
    printf "Build error"
    exit 1
fi

# Stop container
printf "\nStopping existing container\n"
docker stop "$containerName" && docker rm "$containerName"

# Start container
printf "\nStarting container '$containerName'\n"
docker run --name "$containerName" -d -p 10000:10000 -p 10001: "$imageName"
if [ $? -eq 0 ]; then
    printf "\nSuccess!" 
fi
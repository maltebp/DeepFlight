#!/usr/bin/env bash

scp -r -i "$DF_SERVER_KEY" Dockerfile source startContainer.sh requirements.txt df-user@maltebp.dk:./DeepFlight/UniverseUpdater &&\
ssh -i "$DF_SERVER_KEY" df-user@maltebp.dk "cd DeepFlight/UniverseUpdater &&  ./startContainer.sh"
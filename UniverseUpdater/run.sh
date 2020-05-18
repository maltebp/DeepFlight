#!/usr/bin/env bash

scp -r -i "$AMAZON_KEY" Dockerfile source startContainer.sh requirements.txt df-user@maltebp.dk:./DeepFlight/UniverseUpdater &&\
ssh -i "$AMAZON_KEY" df-user@maltebp.dk "cd DeepFlight/UniverseUpdater &&  ./startContainer.sh"
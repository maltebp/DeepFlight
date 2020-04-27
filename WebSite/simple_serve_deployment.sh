#!/bin/bash

# This is a real deployment build
# Served with the program "serve"

sudo npm run build

sudo npm install -g server

sudo serve -s build -l 80

echo done

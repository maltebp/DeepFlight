#!/bin/bash

# build routed to port 80, using the Dockerfile
# source: https://mherman.org/blog/dockerizing-a-react-app/

echo Production build of deep flight
echo This may take a long time...

docker build -f Dockerfile.PRODUCTION -t df:pro .

docker run -itd --rm -p 80:80 df:pro

echo try localhost:80 now

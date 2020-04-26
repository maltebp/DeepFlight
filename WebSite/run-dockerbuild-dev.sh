#!/bin/bash

# 'run npm' routed to port 80, using the Dockerfile
# Should update if you update the files.

echo "Development build of deep flight"

docker build -f Dockerfile.development -t df:dev .

docker run -itd -p 80:3000 df:dev

echo try localhost:80 now

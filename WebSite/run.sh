

printf "Deploying Website to server\n"
scp -q -r -i "$DF_SERVER_KEY" ../WebSite df-user@maltebp.dk:./DeepFlight &&\
ssh -i "$DF_SERVER_KEY" df-user@maltebp.dk "cd DeepFlight/WebSite && ./startContainer.sh"
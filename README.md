# Deep Flight
This is the repository for the entire backend system for the game 'Deep Flight'.  
The game application, including download and information, can be found [here](https://github.com/maltebp/DeepFlightGame), and the game website can be found at deepflight.maltebp.dk.


_DTU Project_  
The game backend (this repo) was a hand-in in the DTU course '62597 Backend Development, Operations and Distributed Systems' spring 2020.

## This repository
... contains a folder for each component/program in the system. An overview of the components can be seen in the [network topology](#network-topology), and are also described here:

 

 - _GameAPI (Java)_  
An API offering certain information from the game database as resources.

 - _UserAPI (Java)_  
Handles authentication of users by using JWT. User information exists in the public user API offered by the project course. The UserAPI also recognizes certain predefined users, which are defined in the source code of the API.

 - _TrackGenerator (C)_  
 A standalone C-program which only purpose is to procedurally generate the track data based on some given parameters (noise, length etc). It doesn't generate track metadata such as names, color etc, nor publishes it

 - _Universeupdater (Python)_   
 Handles the updating of universe, including the generation of new rounds and its tracks (using the TrackGenerator), and rating of the users based on their times.
 
- _Game Database (MongoDB)_  
The database containing all information about the game such as tracks, rounds and user ratings (note: usernames and passwords are not stored in this database). _Note: this obviously doesn't have a folder in this repo._ 

 - _GameAPI-TUI_  
 Just an non-important program required for the course hand-in.

## Network Topology
![Alt text](network_topology.png?raw=true "Title")

Diagram displaying all components (programs) in the entire Deep Flight system (backend and clients), and how they communicate with eachother.  
The direction of the arrows signals which component contacts which (i.e. Game contacts GameAPI and UserAPI and not the other way around).

__Outdated__:  
The UniverseUpdater and Prometheus server are no longer deployed to the dist.saluton.dk server, but are also deployed to maltebp.dk.


## Deployment
All online components are deployed at maltebp.dk, and the public urls are:

 - Website:  __deepflight.maltebp.dk__
 - GameAPI:  __maltebp.dk:10000__ 
 - UserAPI:  __maltebp.dk:7000__

_Note:_ the UniverseUpdater doesn't expose any public ports, except for a Prometheus port.

__How to deploy:__  
In order to deploy the components you must do the following:


 1. Have your public SSH key registered in user `df-user` on the maltebp.dk server by the admin (me, Malte)

 2. Set a global environment variable named `DF_SERVER_KEY` to the absolute path to your registered SSH key (i.e. do this by `export DF_SERVER_KEY=<path/to/private/key>` in your bash shell, or add it as an environment variable in Windows)

 3. For each component you want to deploy, go into the component's folder, and run the `run.sh` script.  
    This will build the project, copy it to the server and start the approriate Docker container.


__Test__:  
All backend components may be tested by running the [Postman Collection](DeepFlight_PostMan_Tests.postman_collection.json) in this repository. This collection will test almost all the available endpoints, in order to check if the system is fully up and running.
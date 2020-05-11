# Deep Flight Universe Updater

This is a backend application for the Deep Flight Game.
Its purpose is to update the game universe by rating
users and generated new rounds, including new tracks.

## Running the program

	1) Open terminal in the directory
	2) Install required packages:
		pip install -r requirements.txt
	3) Run the program:
		python source.updater.universeupdater

WARNING: The program is already running on a server, and
multiple this version contacts the same official database.
Running multiple instances at the same time may interfere
with the consistency of the database, and cause the entire
system to CRASH!
//
// Created by malte on 09-Apr-20.
//


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

#include "planet.h"
#include "track.h"



// Generates a name, which isn't in use for that planet
void generateName(Track *track, Planet *planet, TrackList *existingTracks){
    srand(time(0));

    int nameExists = 0;

    do{
        // Generate track name prefix (i.e. 'A1234')
        char trackId[10];
        trackId[0] = (char) (65 + rand()%25);
        sprintf(trackId+1, "%d", rand()%1000);

        // Use sprintf instead of strcat for first
        // string to be "appended" to overwrite existing
        // data.
        sprintf(track->name,"%s", planet->name);
        strcat(track->name, " ");
        strcat(track->name, trackId);

        // Check if name exists in list
        if( existingTracks != NULL ){
            Track** trackList = existingTracks->list;
            for( int i=0; i<existingTracks->size; i++){
                // If strcmp returns 0, they are identical
                if( strcmp(track->name, trackList[i]->name) == 0 ){
                    nameExists = 1;
                    break;
                }
                trackList++;
            }
        }

    }while(nameExists);
}



// Generate a seed, which isn't in use already
long generateSeed(TrackList *existingTracks){
    long seed;
    int seedExists = 0;

    srand(time(0));

    do{
        // Generate new seed
        seed = rand();

        if( existingTracks != NULL ) {
            // Check if seed exists in list
            Track **trackList = existingTracks->list;
            for (int i = 0; i < existingTracks->size; i++) {
                if (trackList[i]->seed == seed) {
                    seedExists = 1;
                    break;
                }
                trackList++;
            }
        }
    }while(seedExists);

    return seed;
}


Track* generateTrackFromSeed(Planet *planet, TrackList* existingTracks, long seed){
    Track *track = (Track*) malloc(sizeof(Track));
    track->planet = planet;

    generateName(track, planet, existingTracks);


    return track;
}


Track* generateTrack(Planet *planet, TrackList* existingTracks){
    long seed = generateSeed(existingTracks);
    return generateTrackFromSeed(planet, existingTracks, seed);
}
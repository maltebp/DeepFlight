//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_DBCONNECTION_H
#define TRACKGENERATOR_DBCONNECTION_H

#include "planet.h"
#include "track.h"

/*
 * Fetches all Planets from the database.
 * Memory is dynamically allocated, so remember to free.
 *
 * numPlanets:  Where the number of loaded planets will be stored.
 *
 * returns:  Pointer to the array of Planets
 */
Planet** getPlanets( int* numPlanets );


/*
 * Loads all >meta data< (name, id, seed etc) of all Tracks
 * from the given Planet.
 * Memory is dynamically allocated for the Tracks, so remember
 * to free, when done using it.
 *
 * planet:  Address of Planet to load metadata Tracks for
 * tracks:  Pointer to where the number of Tracks loaded will be stored
 *
 * returns: Array of pointers to the loading Tracks
 */
TrackList*  getPlanetTracks( Planet *planet, int *numTracks  );


/*
 * Stores the given track in the database.
 *
 * track:   Track to store
 */
void addTrack( Track* track );


#endif //TRACKGENERATOR_DBCONNECTION_H

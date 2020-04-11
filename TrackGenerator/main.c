#include <stdio.h>
#include <string.h>

#include "model/planet.h"
#include "model/track.h"
#include "generation/generation.h"
#include "generation/trackdata.h"
#include "generation/trackdata_test.h"


int main() {


   /* Planet planet;
    strcpy(planet.name, "Test Planet");


    Track* track = generateTrack(&planet, NULL);

    printf("\n\nGenerated track");
    Track_print(track);*/

    //TODO: Move the setup of the planet to a proper place

    // Setup planet
    Planet planet;
    planet.color[0] = (char) 100;
    planet.color[1] = (char) 200;
    planet.color[2] = (char) 175;
    strcpy(planet.name, "TestPlanet");
    planet.curveFactor = 10;
    planet.lengthFactor = 10;
    planet.stretchFactor = 10;
    planet.widthFactor = 10;
    planet.widthNoise = 10;


    Track* track = generateTrackFromSeed(&planet, NULL, 0);

/*
    track->id = 16;
    printf("Size of id: %d\n", sizeof(track->id));
    strcpy(track->name, "Planet ABC1234");
*/

/*    TrackData* trackData = TrackData_create();
    TrackData_setBlock(trackData, 0, 0, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 10, 10, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 20, 20, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 40, 40, BLOCK_TYPE_SPACE);

    TrackData_storeAsBinary(track, trackData);

    TrackData_free(trackData);*/

    Track_saveToFile(track, "C:\\Users\\malte\\VisualStudioProjects\\DeepFlight\\DeepFlight\\bin\\Windows\\x86\\Debug\\testtrack.dft");
    Track_free(track);

    return 0;
}


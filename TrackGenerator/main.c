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


    TrackData_Test_random();

   //test_TrackData();
/*
    TrackData* trackData = TrackData_create();

    TrackData_setBlock(trackData, 0, 0, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 5, 5, BLOCK_TYPE_BORDER);
    TrackData_setBlock(trackData, -100,-1000, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, -99,-4999, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 20,230, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, -1234,500, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 1000,200, BLOCK_TYPE_SPACE);

    TrackData_forEachBlock(trackData, &Block_print, 1);

    TrackData_print(trackData);

    TrackData_free(trackData);*/

    return 0;
}


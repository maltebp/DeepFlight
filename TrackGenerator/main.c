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


    //TrackData_Test_random();

   //test_TrackData();
    TrackData* trackData = TrackData_create();

    TrackData_setBlock(trackData, 0, 0, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 5, 5, BLOCK_TYPE_BORDER);
    TrackData_setBlock(trackData, -100,-1000, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, -99,-4999, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 20,230, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, -1234,500, BLOCK_TYPE_SPACE);
    TrackData_setBlock(trackData, 1000,200, BLOCK_TYPE_SPACE);


    TrackData_addCheckpoint(trackData, 100, 100);
    TrackData_addCheckpoint(trackData, -1, -1);
    TrackData_addCheckpoint(trackData, 0, 0);


    TrackBinaryData* binaryTrack = TrackData_toBinaryData(trackData);
    if( binaryTrack == NULL ) return 0;
    void* ptr = binaryTrack->data;
    size_t size = 0;
    while(1){
        int x = *((int*) (ptr+size));
        size += sizeof(int);
        int y = *((int*) (ptr+size));
        size += sizeof(int);
        char type = *((char*) (ptr+size));
        size += sizeof(char);

        printf("%d:\tx=%d, y=%d, type=%d\n", size-SIZEOF_BLOCK, x, y, type);

        if( x == 0 && y == 0 && type == 0 ){
            printf("Reached end block\n");
            break;
        }
    };



    while( size < binaryTrack->size ){
        int x = *((int*) (ptr+size));
        size += sizeof(int);
        int y = *((int*) (ptr+size));
        size += sizeof(int);
        printf("%d:\tx=%d, y=%d\n", size-SIZEOF_BLOCK, x, y);
    }

    TrackBinaryData_free(binaryTrack);
    TrackData_free(trackData);


    return 0;
}


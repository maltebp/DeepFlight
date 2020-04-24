//
// Created by malte on 09-Apr-20.
//

#include <stdio.h>
#include <malloc.h>

#include "track.h"

Track* Track_create(){
    Track* track = (Track*) calloc(1, sizeof(Track)) ;
    return track;
}

void Track_free(Track* track){
    if( track->data != NULL ){
        free(track->data);
    }
    free(track);
}

void Track_print(Track *track){
    printf(
            "Track( startDir=%0.3f, data=%0.3fmb,  )",
            track->startDirection, track->data_size/1000000.0
    );
}


void Track_saveToFile(Track* track, const char* fileName){

    printf("\nStoring Track to file '%s'\n", fileName);
    FILE *file = fopen(fileName, "wb");

   /* // ID
    fwrite(&track->id, 8, 1, file);

    // Seed
    fwrite(&track->seed, 8, 1, file);

    // Name
    char* c = track->name;
    while(*c != '\0'){
        fwrite(c, sizeof(char), 1, file);
        c++;
    }
    fwrite(c, sizeof(char), 1, file); // Append null


    // Planet id
    fwrite(&track->planet->id, 8, 1, file);

    // Planet name
    c = track->planet->name;
    while(*c != '\0'){
        fwrite(c, sizeof(char), 1, file);
        c++;
    }
    fwrite(c, sizeof(char), 1, file); // Append null

    // Planet color
    printf("Planet Colors: (%d, %d, %d)\n", track->planet->color[0], track->planet->color[1], track->planet->color[2]);
    fwrite(&track->planet->color[0], sizeof(char), 1, file);
    fwrite(&track->planet->color[1], sizeof(char), 1, file);
    fwrite(&track->planet->color[2], sizeof(char), 1, file);

    // Length
    fwrite(&track->length, sizeof(int), 1, file);



    // Data size
    fwrite(&track->data_size, sizeof(int), 1, file);
*/

    // Starting position
    fwrite(&track->startingX, sizeof(int), 1, file);
    fwrite(&track->startingY, sizeof(int), 1, file);

    // Starting direction
    fwrite(&track->startDirection, sizeof(double), 1, file);

    // Track (block) data
    if( track->data == NULL )
        printf("WARNING: Track data is NULL\n");
    else{
        fwrite(track->data, sizeof(char), track->data_size, file);
    }

    fclose(file);

    printf("Storing Track: Done!\n");
}


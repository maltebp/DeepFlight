//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_TRACK_H
#define TRACKGENERATOR_TRACK_H

#include <stdio.h>
#include <stdint.h>

#include "planet.h"


typedef struct TrackImage{

} TrackImage;


typedef struct Track {
    char name[100];
    int64_t id;
    int64_t seed;
    Planet *planet;
    TrackImage *image;
    int startingX, startingY;
    double startDirection;
    double length;
    int numCheckpoints;
    size_t data_size;
    char *data;
} Track;

typedef struct TrackList {
    int size;
    Track** list;
} TrackList;


void Track_print(Track*);


Track* Track_create();

/*
 * Frees the Track, including the freeing 'data' and the 'image'.
 * It does NOT free the 'planet'.
 */
void Track_free(Track*);



void Track_saveToFile(Track* track, const char* fileName);



#endif //TRACKGENERATOR_TRACK_H

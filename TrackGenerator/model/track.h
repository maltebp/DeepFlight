//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_TRACK_H
#define TRACKGENERATOR_TRACK_H

#include "planet.h"


typedef struct TrackImage{

} TrackImage;


typedef struct Track {
    char name[100];
    long id;
    long seed;
    Planet *planet;
    int numCheckpoints;
    float resistance;
    double length;
    TrackImage *image;
    void *data;
} Track;


typedef struct TrackBlock {
    char size;
    int x;
    int y;

} TrackBlock;


typedef struct TrackCheckpoint {
    int x;
    int y;
} TrackCheckpoint;


typedef struct TrackList {
    int size;
    Track** list;
} TrackList;


void Track_print(Track*);



#endif //TRACKGENERATOR_TRACK_H

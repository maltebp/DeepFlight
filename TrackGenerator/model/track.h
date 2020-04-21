//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_TRACK_H
#define TRACKGENERATOR_TRACK_H

#include <stdio.h>
#include <stdint.h>


typedef struct Track {
    int startingX, startingY;
    double startDirection;
    size_t data_size;
    char *data;
} Track;


Track* Track_create();
void Track_free(Track*);
void Track_print(Track*);

void Track_saveToFile(Track* track, const char* fileName);



#endif //TRACKGENERATOR_TRACK_H

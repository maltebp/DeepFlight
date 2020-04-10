//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_GENERATOR_H
#define TRACKGENERATOR_GENERATOR_H

#include "planet.h"
#include "track.h"

Track* generateTrackFromSeed(Planet *planet, TrackList *existingTracks, long seed);

Track* generateTrack(Planet *planet, TrackList *existingTracks);




typedef struct Node{
    struct Node* next;
    int x;
    int y;
} Node;


typedef struct NodePool{
    struct NodePool* next;
    Node* nodes;
    int used;
    int size;
} NodePool;


Node* NodePool_getNode(NodePool* pool);

NodePool* NodePool_create(int size);

void NodePool_free(NodePool* pool);



#endif //TRACKGENERATOR_GENERATOR_H

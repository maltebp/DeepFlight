//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_GENERATOR_H
#define TRACKGENERATOR_GENERATOR_H

#include "../model/track.h"
#include "genparams.h"


#define START_END_NODE_OFFSET 8
#define CHECKPOINT_NODE_STEPSIZE 35



Track* generateTrack(GenParams*, unsigned int seed);




typedef struct Node{
    struct Node* next;
    int x;
    int y;
    float direction;
} Node;


typedef struct NodePool{
    struct NodePool* next;
    Node* nodes;
    int used;
    unsigned int size;
} NodePool;


Node* NodePool_getNode(NodePool* pool);

int NodePool_totalUsed(NodePool*);

NodePool* NodePool_create(unsigned int size);

void NodePool_free(NodePool* pool);



#endif //TRACKGENERATOR_GENERATOR_H

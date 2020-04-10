//
// Created by malte on 09-Apr-20.
//


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <math.h>

#include "planet.h"
#include "track.h"
#include "generation.h"


Node* generateCenterPath(Track *track, NodePool* nodePool);
void generateBlocks(Track *track);


// Generates a name, which isn't in use for that planet
void generateName(Track *track, TrackList *existingTracks){
    srand(time(0));

    int nameExists = 0;
    char trackName[10];
    trackName[7] = (char) 0; // adding end of string

    do{
        // Add 4 letters
        trackName[0] = (char) (65+rand()%25);
        trackName[1] = (char) (65+rand()%25);
        trackName[2] = (char) (65+rand()%25);
        trackName[3] = (char) (65+rand()%25);

        // add 3 numbers
        trackName[4] = (char) (48+rand()%10);
        trackName[5] = (char) (48+rand()%10);
        trackName[6] = (char) (48+rand()%10);

        // Check if name exists in list
        if( existingTracks != NULL ){
            Track** trackList = existingTracks->list;
            for( int i=0; i<existingTracks->size; i++){
                // If strcmp returns 0, they are identical
                if( strcmp(trackName, trackList[i]->name) == 0 ){
                    nameExists = 1;
                    break;
                }
                trackList++;
            }
        }

    }while(nameExists);
    strcpy(track->name, trackName);
}



// Generate a seed, which isn't in use already
long generateSeed(TrackList *existingTracks){
    long seed;
    int seedExists = 0;

    srand(time(0));

    do{
        seed = rand();

        if( existingTracks != NULL ) {
            // Check if seed exists in list
            Track **trackList = existingTracks->list;
            for (int i = 0; i < existingTracks->size; i++) {
                if (trackList[i]->seed == seed) {
                    seedExists = 1;
                    break;
                }
                trackList++;
            }
        }
    }while(seedExists);

    return seed;
}


Track* generateTrackFromSeed(Planet *planet, TrackList* existingTracks, long seed){
    Track *track = (Track*) malloc(sizeof(Track));
    track->planet = planet;

    generateName(track, existingTracks);

    generateBlocks(track);


    return track;
}


Track* generateTrack(Planet *planet, TrackList* existingTracks){
    long seed = generateSeed(existingTracks);
    return generateTrackFromSeed(planet, existingTracks, seed);
}



// PATH GENERATION ---------------------------------------------------------------------


void generateBlocks(Track *track){

    NodePool* nodePool = NodePool_create(250);

    Node* pathHead = generateCenterPath(track, nodePool);


    // Checking number of nodes
    int nodeCount = 0;
    Node* current = pathHead;
    while( current != NULL){
        printf("Node: x=%d, y=%d\n", current->x, current->y);
        nodeCount++;
        current = current->next;
    }


    printf("Number of nodes: %d\n", nodeCount);
    printf("Length: %d\n", (int) track->length);


    NodePool_free(nodePool);
}


#define ANGLE_PER_NODE 0.1

/*
 *
 * return: Head node of the linked path
 */
Node* generateCenterPath(Track *track, NodePool* nodePool){
    Planet *planet = track->planet;

    Node* head = NodePool_getNode(nodePool);
    Node* current = head;

    head->x = 0;
    head->y = 0;


    //TODO: Move the setup of the planet to a proper place
    planet->curveFactor = 10;
    planet->lengthFactor = 10;
    planet->stretchFactor = 10;
    planet->widthFactor = 10;
    planet->widthNoise = 10;

    //          base      constant factor                 constant factor
    track->length = 500 + planet->lengthFactor*100   +  rand()%75*planet->lengthFactor;


    double direction = fmod(rand(), M_PI_2);


    double angleFactor = 0;

    double remainingLength = track->length;

    double remainingSegmentLength = -1;

    double stepSize = 0;



    while(remainingLength > 0){
        if(  remainingSegmentLength < 0 ) {
            remainingSegmentLength = 50 + rand()%10*planet->stretchFactor + planet->stretchFactor*5;

            // How much to turn in this segment
            double angle = M_PI_2/30 * (rand()%(planet->curveFactor*10));
            if( angle > M_PI_2*0.75 ) angle = M_PI_2*0.75;

            int nodesInSegment = (int) (angle / ANGLE_PER_NODE)+1;

            stepSize = remainingSegmentLength / nodesInSegment;

            angleFactor = rand()%2 ? 1 : -1;
        }

        direction += ANGLE_PER_NODE*angleFactor;


        Node* next = NodePool_getNode(nodePool);

        next->x = (int) (current->x + sin(direction) * stepSize);
        next->y = (int) (current->y + cos(direction) * stepSize);

        current->next = next;
        current = next;

        remainingLength -= stepSize;
        remainingSegmentLength -= stepSize;
    }
    return head;
}



NodePool* NodePool_create(int size){
    NodePool* pool = (NodePool*) malloc(sizeof(NodePool));
    pool->size = size;
    pool->used = 0;
    pool->nodes = (Node*) malloc(sizeof(Node)*size);
    pool->next = NULL;
    return pool;
}


void NodePool_free(NodePool* pool){
    NodePool* next = pool;
    while( next != NULL ){
        next = pool->next;
        free(pool->nodes);
        free(pool);
    }
}

Node* NodePool_getNode(NodePool* pool){
    NodePool* current = pool;
    while(1){

        // Check if node is available in this pool
        if( current->used < current->size){
            current->used++;
            Node* node = current->nodes+(current->used-1);
            node->x = 0;
            node->y = 0;
            node->next = NULL;
            return node;
        }

        // Check if a pool after this exists
        if( current->next != NULL ){
            current = current->next;
            continue;
        }

        // Create new pool and return node from there
        current->next = NodePool_create(current->size);
        current = current->next;
    }
}















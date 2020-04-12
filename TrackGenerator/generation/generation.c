//
// Created by malte on 09-Apr-20.
//


#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <math.h>

#include "../model/planet.h"
#include "../model/track.h"
#include "generation.h"
#include "trackdata.h"

// "Private" helper functions for generating a track
Node** generateSidePaths(Track*, Node* centerPath, NodePool*);
Node* generateCenterPath(Track *track, NodePool* nodePool);
void generateBlocks(Track *track, TrackData*, Node** sidePaths, int* blocksGenerated);
void generateStartingPos(Track* track, Node* centerPath);
unsigned int generateSeed(TrackList* existingTracks);
void generateName(Track *track, TrackList* existingTracks);
void generateCheckpoints(TrackData*, Node* centerPath, int centerPathLength);


/*
 * Generates a track from a random (time based) seed.
 */
Track* generateTrack(Planet *planet, TrackList* existingTracks){
    unsigned int seed = generateSeed(existingTracks);
    return generateTrackFromSeed(planet, existingTracks, seed);
}


// Generate a seed, which isn't in use already
unsigned int generateSeed(TrackList *existingTracks){
    unsigned int seed;
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



/*
 * Generates a Track from a specified seed.
 * The name is still randomly generated.
 */
Track* generateTrackFromSeed(Planet *planet, TrackList* existingTracks, unsigned seed){

    printf("\nGenerating new track\n");
    clock_t startTime = clock();

    Track *track = (Track*) calloc(1, sizeof(Track));
    track->planet = planet;
    track->seed = seed;

    // Data structure used to hold information
    // when generating the binary data for the track
    // (checkpoints and blocks)
    TrackData* trackData = TrackData_create();

    generateName(track, existingTracks);

    srand(seed);

    // Generate Node Paths
    NodePool* nodePool = NodePool_create(500);
    Node* centerPath = generateCenterPath(track, nodePool);
    int centerPathLength = NodePool_totalUsed(nodePool);
    Node** sidePaths = generateSidePaths(track, centerPath, nodePool);

    generateStartingPos(track, centerPath);
    generateCheckpoints(trackData, centerPath, centerPathLength);
    track->numCheckpoints = trackData->numCheckpoints;

    int blocksGenerated = 0;
    generateBlocks(track, trackData, sidePaths, &blocksGenerated);
    TrackData_storeAsBinary(track, trackData);

    clock_t endTime = clock();
    double elapsedTime = ((double) endTime-startTime) / CLOCKS_PER_SEC;

    printf("Generated Track:\n");
    Track_print(track);

    printf("\nTime:\t %0.4f\n", elapsedTime);
    printf("Nodes:\t %d\n", NodePool_totalUsed(nodePool));
    printf("Blocks:\t %d\n", blocksGenerated);

    // Free up used memory
    TrackData_free(trackData);
    free(sidePaths);
    NodePool_free(nodePool);

    return track;
}



// Generates a name, which isn't in use for that planet
void generateName(Track *track, TrackList *existingTracks){
    printf("Generating name\n");
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




// PATH GENERATION ---------------------------------------------------------------------

#define ANGLE_PER_NODE 0.025
#define PI2 (M_PI*2)

/*
 *
 * return: Head node of the linked path
 */
Node* generateCenterPath(Track *track, NodePool* nodePool){
    Planet *planet = track->planet;

    // Generate the track length
    track->length = 1250 +  planet->lengthFactor*175   +  (rand()%70)*planet->lengthFactor;

    double direction = fmod(rand(), PI2);
    double angleFactor = 0;
    double remainingLength = track->length;
    double remainingSegmentLength = -1;
    double stepSize = 0;

    Node* head = NodePool_getNode(nodePool);
    Node* current = head;

    head->x = 0;
    head->y = 0;
    head->direction = direction;

    while(remainingLength > 0){

        if(  remainingSegmentLength < 0 ) {

            // Length of this segment
            remainingSegmentLength = 100 + (rand() %10)*planet->stretchFactor*2 + planet->stretchFactor*10;

            // How much to turn in this segment
            double angle = M_PI * ((rand()%500)/5000.0) * planet->curveFactor/5 + planet->curveFactor/15.0;
            if( angle > PI2/2.0 ) angle = PI2*0.50;

            int nodesInSegment = (int) (angle / ANGLE_PER_NODE)+1;

            stepSize = remainingSegmentLength / nodesInSegment;

            angleFactor = rand()%2 ? 1 : -1;
        }

        direction += ANGLE_PER_NODE*angleFactor;

        Node* next = NodePool_getNode(nodePool);

        next->x = (int) (current->x + sin(direction) * stepSize);
        next->y = (int) (current->y + cos(direction) * stepSize);
        next->direction = (float) direction;

        current->next = next;
        current = next;

        remainingLength -= stepSize;
        remainingSegmentLength -= stepSize;
    }
    return head;
}




Node** generateSidePaths(Track* track, Node* centerPath, NodePool* nodePool){
    Node** sidePaths = (Node**) malloc(sizeof(Node*)*2);

    Node* right = NULL;
    Node* left = NULL;

    double minWidth = 10 + track->planet->widthFactor*1.25;
    double maxWidth = minWidth * (1 + sqrt(track->planet->widthNoise)/6);
    double width = maxWidth/1.5;

    Node* center = centerPath;
    int numCenterNodes = NodePool_totalUsed(nodePool);


    int nodeIndex = 0;
    while( center != NULL ){
        if( nodeIndex % 2 == 0 ){

            // Create next nodes
            if( right == NULL ){
                right = NodePool_getNode(nodePool);
                left = NodePool_getNode(nodePool);
                sidePaths[0] = right;
                sidePaths[1] = left;
            }else{
                right->next = NodePool_getNode(nodePool);
                left->next = NodePool_getNode(nodePool);
                right = right->next;
                left = left->next;
            }


            // Update width
            double widthAdjustment = 2 + (rand()%4)/20.0;
            widthAdjustment *= track->planet->widthNoise/7.0;
            if( rand()%2 ) widthAdjustment *= -1;

            width += widthAdjustment;
            if( width > maxWidth ) width = maxWidth;
            if( width < minWidth ) width = minWidth;

            if( nodeIndex < 8){
                width *= nodeIndex/32.0 + 0.25;
            }
            else if( nodeIndex > numCenterNodes-8){
                width *= (numCenterNodes-nodeIndex)/32.0 + 0.25;
            }

            // How much the center of the width calculation is offset from
            // the actual center node
            double centerOffset = (rand()%100)/75.0 * sqrt(track->planet->widthNoise);
            if( rand()%2 ) centerOffset *= -1;

            // Calculate side nodes
            right->x = (int) (center->x + sin(center->direction+M_PI_2) * (width-centerOffset));
            right->y = (int) (center->y + cos(center->direction+M_PI_2) * (width-centerOffset));
            left->x = (int) (center->x + centerOffset + sin(center->direction-M_PI_2) * (width+centerOffset));
            left->y = (int) (center->y + centerOffset + cos(center->direction-M_PI_2) * (width+centerOffset));

        }
        nodeIndex++;
        center = center->next;
    }
    return sidePaths;
}




// ===============================================================================================================================


/*
 * Generate the starting position (x,y) and the starting
 * direction, by using the center path of generated Nodes.
 */
void generateStartingPos(Track* track, Node* centerPath){
    Node* startingNode = centerPath;
    for(int i=0; i<START_END_NODE_OFFSET; i++){
        startingNode = startingNode->next;
    }
    track->startingX = startingNode->x;
    track->startingY = startingNode->y;

    // Set the starting direction
    Node* directionNode = startingNode->next->next;
    track->startDirection = atan2(directionNode->y-startingNode->y, directionNode->x-startingNode->x);
    if (track->startDirection < 0) { track->startDirection += 2 * M_PI; }
}


/*
 * Generate the checkpoints, using the center path of nodes.
 */
void generateCheckpoints(TrackData *trackData, Node* centerPath, int centerPathLength){
    int endNodeIndex = centerPathLength - START_END_NODE_OFFSET;

    int checkpointCountdown = START_END_NODE_OFFSET + CHECKPOINT_NODE_STEPSIZE;
    Node* node = centerPath;
    for( int i=0; i<endNodeIndex; i++) {
        if( checkpointCountdown <= 0 ){
            checkpointCountdown = CHECKPOINT_NODE_STEPSIZE + rand()%4-2;
            TrackData_addCheckpoint(trackData, node->x, node->y);
        }
        checkpointCountdown--;
        node = node->next;
    }

    // Check if we stopped "prematurely"
    if( checkpointCountdown > START_END_NODE_OFFSET*0.5 ){
        TrackData_addCheckpoint(trackData, node->x, node->y);
    }
}





// =============================================================================================================================================
// BLOCK GENERATION

/*
 * Generic test of whether a point is within a triangle or not.
 */
int isPointWithinTriangle(int x, int y, Node** nodes){
    // Area =     0.5 *(-p1y        *  p2x       +    p0y     *(-p1x         +      p2x)    + p0x        *(p1y         - p2y) + p1x*p2y);
    double area = 0.5 *(-nodes[1]->y*nodes[2]->x + nodes[0]->y*(-nodes[1]->x + nodes[2]->x) + nodes[0]->x*(nodes[1]->y - nodes[2]->y) + nodes[1]->x * nodes[2]->y );
    double s = 1/(2*area)*(nodes[0]->y * nodes[2]->x - nodes[0]->x*nodes[2]->y + (nodes[2]->y - nodes[0]->y)*x + (nodes[0]->x - nodes[2]->x)*y);
    double t = 1/(2*area)*(nodes[0]->x * nodes[1]->y - nodes[0]->y*nodes[1]->x + (nodes[0]->y - nodes[1]->y)*x + (nodes[1]->x - nodes[0]->x)*y);
    return s >= 0 && t >= 0 && (1-s-t) >= -0.01;
}


void generateBlocks(Track *track, TrackData* trackData, Node** sidePaths, int* blocksGenerated ){
    Node* right = sidePaths[0];
    Node* left = sidePaths[1];
    Node* nodes[4];

    while(1){
        if( right == NULL || right->next == NULL || left == NULL || left->next == NULL)
            break;

        // The nodes we are working on
        nodes[0] = right;
        nodes[1] = right->next;
        nodes[2] = left;
        nodes[3] = left->next;

        int minX = nodes[0]->x;
        int maxX = nodes[0]->x;
        int minY = nodes[0]->y;
        int maxY = nodes[0]->y;

        for(int i=1; i<4; i++){
            if(nodes[i]-> x <= minX) minX = nodes[i]->x;
            if(nodes[i]-> x > maxX) maxX = nodes[i]->x;
            if(nodes[i]-> y <= minY) minY = nodes[i]->y;
            if(nodes[i]-> y > maxY) maxY = nodes[i]->y;
        }


        Node* triangle1[] = {nodes[0], nodes[1], nodes[2]};
        Node* triangle2[] = {nodes[3], nodes[1], nodes[2]};

        // For each block within the x, y range
        for( int x=minX; x<maxX; x++){
            for( int y=minY; y<maxY; y++){
                if( isPointWithinTriangle(x,y,triangle1) || isPointWithinTriangle(x,y,triangle2) ) {
                    TrackData_setBlock(trackData, x, y, BLOCK_TYPE_SPACE);
                    //TrackData_setBlock(trackData, x+1, y, BLOCK_TYPE_SPACE);
                    //TrackData_setBlock(trackData, x, y+1, BLOCK_TYPE_SPACE);
                    //printf("Adding block:\t(%d,%d)\n", x, y);
                }
            }
        }

        right = right->next;
        left = left->next;
    }
    *blocksGenerated = trackData->numBlocks;
}


// ==========================================================================================================
// Node pool


NodePool* NodePool_create(unsigned int size){
    NodePool* pool = (NodePool*) malloc(sizeof(NodePool));
    pool->size = size;
    pool->used = 0;
    pool->nodes = (Node*) calloc(size, sizeof(Node));
    pool->next = NULL;
    return pool;
}


void NodePool_free(NodePool* pool){
    NodePool* current = pool;
    NodePool* next;
    while( current != NULL ){
        next = current->next;
        free(current->nodes);
        free(current);
        current = next;
    }
}

Node* NodePool_getNode(NodePool* pool){
    NodePool* current = pool;
    while(1){
        // Check if node is available in this pool
        if( current->used < current->size){
            Node* node = current->nodes + current->used;
            current->used++;
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

int NodePool_totalUsed(NodePool* nodePool){
    int totalUsed = 0;
    NodePool* next = nodePool;
    while( next != NULL) {
        totalUsed += next->used;
        next = next->next;
    }
    return totalUsed;
}















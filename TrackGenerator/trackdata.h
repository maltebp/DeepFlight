//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_TRACKDATA_H
#define TRACKGENERATOR_TRACKDATA_H

#include <stdio.h>

// Width and height of Chunk
#define CHUNK_SIZE 10

// We're using defines instead of enum, as enum are int sized
#define BLOCK_TYPE_WALL 0
#define BLOCK_TYPE_SPACE 1
#define BLOCK_TYPE_BORDER 2


typedef struct Block {
    int x, y;
    char type;
} Block;



typedef struct Chunk {
    struct Chunk* next;
    int x, y;
    struct Block blocks[CHUNK_SIZE][CHUNK_SIZE];
    struct Cell *cell[CHUNK_SIZE][CHUNK_SIZE];
} Chunk;


typedef struct Checkpoint {
    struct Checkpoint *next;
    int x, y;
} Checkpoint;


typedef struct TrackData {
    Chunk* firstChunk;
    int numChunks;
    int minX, minY, maxX, maxY;
    int numBlocks; // Non-wall blocks
    Checkpoint *firstCheckpoint;
} TrackData;


// Tracka Data functions
TrackData* TrackData_create();
void TrackData_free(TrackData* trackData);
Block* TrackData_getBlock(TrackData* trackData, int x, int y, int createNew);
void TrackData_setBlock(TrackData* trackData, int x, int y, char blockType);
void TrackData_saveToFile(TrackData* trackData, const char* fileName);
void TrackData_forEachBlock(TrackData* trackData, void (*blockCallback)(Block* block), int skipWalls);
void TrackData_print(TrackData* trackData);

void Block_print(Block* block);
int Block_equals(Block* block1, Block* block2);



int test_TrackData();


#endif //TRACKGENERATOR_TRACKDATA_H

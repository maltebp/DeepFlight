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


#define SIZEOF_BLOCK 9

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

#define SIZEOF_CHECKPOINT 8
typedef struct Checkpoint {
    struct Checkpoint *next;
    int x, y;
} Checkpoint;


typedef struct TrackData {
    Chunk* firstChunk;
    int numChunks;
    int numBlocks; // Non-wall blocks
    int numCheckpoints;
    Checkpoint *firstCheckpoint;
} TrackData;


typedef struct TrackBinaryData {
    void* data;
    size_t size; // number of bytes
    void* last;
} TrackBinaryData;


// Tracka Data functions
TrackData* TrackData_create();
void TrackData_free(TrackData* trackData);
Block* TrackData_getBlock(TrackData* trackData, int x, int y, int createNew);
void TrackData_setBlock(TrackData* trackData, int x, int y, char blockType);
void TrackData_addCheckpoint(TrackData* trackData, int x, int y);
//void TrackData_saveToFile(TrackData* trackData, const char* fileName);
TrackBinaryData* TrackData_toBinaryData(TrackData* trackData);
void TrackBinaryData_free(TrackBinaryData* data);


void TrackData_forEachBlock(TrackData* trackData, void (*blockCallback)(Block* block, void* data), void* data, int skipWalls);
void TrackData_print(TrackData* trackData);

void Block_print(Block* block);
int Block_equals(Block* block1, Block* block2);


void Checkpoint_print(Checkpoint* checkpoint);


int test_TrackData();


#endif //TRACKGENERATOR_TRACKDATA_H

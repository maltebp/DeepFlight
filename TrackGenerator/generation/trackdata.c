

#include "trackdata.h"

#include <math.h>
#include <stdlib.h>



Chunk* TrackData_getChunk(TrackData* trackData, int x, int y, int createNew);


Chunk* Chunk_create(int chunkX, int chunkY){
    Chunk* chunk = (Chunk*) malloc(sizeof(Chunk));
    chunk->next = NULL;
    chunk->x = chunkX;
    chunk->y = chunkY;

    // Initialize blocks
    for(int y=0; y<CHUNK_SIZE; y++){
        for( int x=0; x<CHUNK_SIZE; x++){
            Block* block = &chunk->blocks[y][x];
            block->x = chunkX+x;
            block->y = chunkY+y;
            block->type = BLOCK_TYPE_WALL;
        }
    }
    return chunk;
}





/* coordinate: Raw world coordinate of the block
 * returns: The Raw world coordinate of the Chunk containing the block, which
 *          is the upper left corner of the block.
 */
int toChunkCoordinate(int coordinate){
    if( coordinate < 0) return (((coordinate*-1-1)/-CHUNK_SIZE)-1) * CHUNK_SIZE;
    return (coordinate/CHUNK_SIZE)*CHUNK_SIZE;


    // -10   ->   -10*-1-1 = 9   ->  9/-10 = 0  ->  0-1 = -1
    // -5   ->   -10*-1-1 = 4   ->  4/-10 = 0  ->  0-1 = -1
    // -11   ->   -11*-1-1 = 10   ->  10/-10 = -1  ->  -1-1 = -2

    // -91   ->   -91*-1-1 = 90  -> 90/-10 = -9 -> -10
    // -99   ->   -99*-1-1 = 98 -> 98/-10  = -9 -> -10


    // 9/10 = 0 -> 0  = 0
}

/*
 * Converts the Block coordinate to the index within the Chunk.
 *
 * Block index in chunks are laid out as:
 * -1       0      1
 *  012345  012345 012345
 *
 * So index 0 is always the left most block within the chunk
 *
 * coordinate: Raw world coordinate of the the block
 * returns: The index of the block within the cell (the index used to dereference the array)
 */
int toBlockIndexInChunk(int coordinate){
    if( coordinate < 0 ) return CHUNK_SIZE - ((coordinate*-1-1) % (CHUNK_SIZE)) -1;
    return coordinate % CHUNK_SIZE;

    // Negative examples
    // -10   ->  -10*-1-1 = 9   ->  9 % 10 = 9   ->  (10-9)-1 = 0
    // -99   ->  -99*-1-1 = 98  -> 98 & 10 = 8   ->  (10-8-1) = 1
    // -1   ->  -1*-1-1 = 0  -> 0 % 10 = 0   ->  (10-0-1) = 9
}


TrackData* TrackData_create(){
    TrackData* trackData = (TrackData*) malloc(sizeof(TrackData));
    trackData->firstCheckpoint = NULL;
    trackData->firstChunk = NULL;
    trackData->numBlocks = 0;
    trackData->numChunks = 0;
    trackData->numCheckpoints = 0;
    return trackData;
}


Block* TrackData_getBlock(TrackData* trackData, int x, int y, int createNew){
    Chunk* chunk = TrackData_getChunk(trackData, x, y, createNew);
    if( chunk == NULL ) return NULL;

    return &chunk->blocks[toBlockIndexInChunk(y)][toBlockIndexInChunk(x)];
}


void TrackData_setBlock(TrackData* trackData, int x, int y, char blockType){
    Block* block = TrackData_getBlock(trackData, x, y, 1);

    // Update number of 'non-wall blocks'
    if( block->type == BLOCK_TYPE_WALL && blockType != BLOCK_TYPE_WALL){
        trackData->numBlocks++;
    }
    else if( block->type != BLOCK_TYPE_WALL && blockType == BLOCK_TYPE_WALL){
        trackData->numBlocks--;
    }

    block->type = blockType;
}

void TrackData_forEachBlock(TrackData* trackData, void (*blockCallback)(Block*, void*), void* data, int skipWalls){
    Chunk* chunk = trackData->firstChunk;
    while(chunk != NULL){
        // Initialize blocks
        for(int y=0; y<CHUNK_SIZE; y++){
            for( int x=0; x<CHUNK_SIZE; x++) {
                Block* block = &chunk->blocks[y][x];
                if( !skipWalls || block->type != 0) {
                    blockCallback(block, data);
                }
            }
        }
        chunk = chunk->next;
    }
}

void TrackData_addCheckpoint(TrackData* trackData, int x, int y){
    Checkpoint* newCheckpoint = (Checkpoint*) malloc(sizeof(Checkpoint));
    if( newCheckpoint == NULL ){
        printf("ERROR: Couldn't allocate Checkpoint for TrackData\n");
        return;
    }

    newCheckpoint->x = x;
    newCheckpoint->y = y;
    newCheckpoint->next = NULL;

    trackData->numCheckpoints++;

    // New CP is first
    if( trackData->firstCheckpoint == NULL){
        trackData->firstCheckpoint = newCheckpoint;
        return;
    }

    // Find tail of linked checkpoint list
    Checkpoint* checkpoint = trackData->firstCheckpoint;
    while( checkpoint->next != NULL ){
        checkpoint = checkpoint->next;
    }
    checkpoint->next = newCheckpoint;
}


/*
 *  Returns Chunk which contains the Block with coordinates (x,y).
 *
 *  createNew:   Whether or not to create a new chunk if the Chunk is null
 *  returns: Pointer to the Chunk, or NULL if it didn't exist and createNew was 0.
 */
Chunk* TrackData_getChunk(TrackData* trackData, int x, int y, int createNew){
    int chunkX = toChunkCoordinate(x);
    int chunkY = toChunkCoordinate(y);

    Chunk* chunk = trackData->firstChunk;
    Chunk* previous = NULL;

    // Check if any chunks contain coordinate
    while( chunk != NULL ){
        if( chunk->x == chunkX && chunk->y == chunkY )
            return chunk;
        previous = chunk;
        chunk = chunk->next;
    }

    // Create new chunk
    if( createNew ){
        Chunk* newChunk = Chunk_create(chunkX, chunkY);
        if( previous == NULL ){
            trackData->firstChunk = newChunk;
        }else{
            previous->next = newChunk;
        }
        trackData->numChunks++;
        return newChunk;
    }

    return NULL;
}

void Block_print(Block* block){
    if( block == NULL )
        printf("NULL\n");
    else
        printf("Block( x=%d, y=%d, type=%d)\n", block->x, block->y, block->type);
}


void TrackData_print(TrackData* trackData){
    printf("TrackData( numChunks=%d, numBlocks=%d, numCheckpoints=%d )\n", trackData->numChunks, trackData->numBlocks, trackData->numCheckpoints );
}

void Checkpoint_print(Checkpoint* checkpoint) {
    printf("Checkpoint( x=%d, y=%d, hasNext=%s)\n", checkpoint->x, checkpoint->y, checkpoint->next == NULL ? "false" : "true");
}


void TrackData_free(TrackData* trackData){
    Chunk* chunk = trackData->firstChunk;
    while(chunk != NULL){
        Chunk* previousChunk = chunk;
        chunk = previousChunk->next;
        free(previousChunk);
    }
    free(trackData);
}



int Block_equals(Block* block1, Block* block2) {
    if( block1 == NULL || block2 == NULL ) return 0;
    if( block1 == block2) return 1; // Pointers are equals
    return (block1->x == block2->x && block1->y == block2->y && block1->type == block2->type);

}




// ===================================================================================================
// BINARY TRACK CONVERSION

typedef struct TrackBinaryData {
    void* data;
    size_t size; // number of bytes
    void* last;
} TrackBinaryData;




void TrackBinaryData_appendCheckpoint(TrackBinaryData* data, Checkpoint* block){
    int* intPtr = data->last;

    *intPtr = block->x;
    intPtr++;
    *intPtr = block->y;
    intPtr++;

    data->last = intPtr;
    data->size += SIZEOF_CHECKPOINT;
}

void TrackBinaryData_appendBlock(TrackBinaryData* data, Block* block){
    int* intPtr = data->last;

    *intPtr = block->x;
    intPtr++;
    *intPtr = block->y;
    intPtr++;

    char* charPtr = (char*) intPtr;
    *charPtr = block->type;
    charPtr++;

    data->last = charPtr;
    data->size += SIZEOF_BLOCK;
}


void storeBlock(Block* block, void* data){
    TrackBinaryData* binaryData = (TrackBinaryData*) data;
    TrackBinaryData_appendBlock(binaryData, block);
}



/*
 * Converts the TrackData (blocks and checkpoints, to a single sequential
 * array of bytes, and stores it in the Track;
 */
void TrackData_storeAsBinary(Track *track, TrackData *trackData) {

    TrackBinaryData *binaryTrack = (TrackBinaryData*) malloc(sizeof(TrackBinaryData));

    // Calculate how allocated memory should should be
    size_t dataSize = (unsigned int) ((trackData->numBlocks+1) * SIZEOF_BLOCK + trackData->numCheckpoints * SIZEOF_CHECKPOINT);

    binaryTrack->data = malloc(dataSize);
    binaryTrack->last = binaryTrack->data;
    binaryTrack->size = 0;

    if (binaryTrack->data == NULL) {
        printf("ERROR: Couldn't allocate %d bytes when converting TrackData!\n", dataSize);
        free(binaryTrack->data);
        free(binaryTrack);
        return;
    }

    // Append each block
    TrackData_forEachBlock(trackData, &storeBlock, binaryTrack, 1);

    // The end of the Block data is signalled by Block which is all 0
    Block endBlock = {0,0,0};
    TrackBinaryData_appendBlock(binaryTrack, &endBlock);


    // Add all checkpoints
    Checkpoint* checkpoint = trackData->firstCheckpoint;
    while(checkpoint != NULL){
        TrackBinaryData_appendCheckpoint(binaryTrack, checkpoint);
        checkpoint = checkpoint->next;
    }

    // Check that size match
    if( binaryTrack->size != dataSize ){
        printf("ERROR: Resulting size (%d) does not equal allocated size (%d), when converting TrackData!\n",
                binaryTrack->size, dataSize);
        free(binaryTrack->data);
        free(binaryTrack);
        return;
    }

    track->data = binaryTrack->data;
    track->data_size = binaryTrack->size;

    free(binaryTrack);
}

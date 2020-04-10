
#include <stdlib.h>
#include "trackdata_test.h"

// Globals used to test the callback
Block* testBlocks = NULL;
int numTestBlocks = 0;
int test_CallbackSuccess = 1;

void testBlockCallback(Block* block){
    if( test_CallbackSuccess ){
        printf("\tChecking: ");
        Block_print(block);
        for( int i=0; i<numTestBlocks; i++){
            if( Block_equals(&testBlocks[i], block) ){
                printf("\tSUCCESS\n");
                return;
            }
        }
        printf("\tFAIL\n");
        test_CallbackSuccess = 0;
    }
}


int TrackData_test(Block* blocks, int numBlocks){
    TrackData* trackData = TrackData_create();

    // Set blocks
    for( int i=0; i<numBlocks; i++ ){
        TrackData_setBlock(trackData, blocks[i].x, blocks[i].y, blocks[i].type);
    }

    // Check 'get'
    printf("\nTesting 'TrackData_getBlock(..)'\n");
    for( int i=0; i<numBlocks; i++ ){
        Block* fetchedBlock = TrackData_getBlock(trackData, blocks[i].x, blocks[i].y, 0);
        int result = Block_equals(fetchedBlock, &blocks[i]);
        printf("\tExpected:\t");
        Block_print(&blocks[i]);
        printf("\tGot: \t\t");
        Block_print(fetchedBlock);
        printf("\t%s\n", result ? "SUCCESS" : "FAIL" );
        if( !result ){
            TrackData_free(trackData);
            return 0;
        }
    }
    printf("'TrackData_getBlock(..)': SUCCESS\n");


    printf("\nTesting 'TrackData_forEachBlock(..)'\n");
    testBlocks = blocks;
    numTestBlocks = numBlocks;
    test_CallbackSuccess = 1;
    TrackData_forEachBlock(trackData, &testBlockCallback, 1);
    if( test_CallbackSuccess ){
        printf("'TrackData_forEachBlock(..)': SUCCESS\n");
        TrackData_free(trackData);
        return 1;
    }else{
        printf("'TrackData_forEachBlock(..)': FAILED\n");
        TrackData_free(trackData);
        return 0;
    }
}



int TrackData_Test_simple(){
    printf("\n\nRUNNING TEST: TrackData_Test_simple\n");
    Block testBlocks[] = {
            {0, 0, 1},
            {-1,-1, 1},
            {-1,0, 2},
            {0,-1, 2},
            {-1000, 1000, 2},
            {-999,-999, 1},
            {5,5, 2},
            {9,9, 1},
            {10,10, 2},
            {-1000,-100, 1},
            {999,999, 2},
            {1000,1000, 2}
    };

    int result = TrackData_test(testBlocks, sizeof(testBlocks)/sizeof(Block));
    if( result )
        printf("\nTrackData_Test_simple: SUCCESS\n");
    else
        printf("\nTrackData_Test_simple: FAILED\n");

    return result;
}


#define RANDOM_TEST_SIZE 1000

int TrackData_Test_random(){
    printf("\n\nRUNNING TEST: TrackData_Test_random\n");

    // Generate RANDOM_TEST_SIZE number of random unique blocks
    Block blocks[RANDOM_TEST_SIZE];
    for(int i=0; i<RANDOM_TEST_SIZE; i++){
        int unique = 0;
        while(!unique){
            unique = 1;

            blocks[i].x = rand() - RAND_MAX/2;
            blocks[i].y = rand() - RAND_MAX/2;
            blocks[i].type = (char) (rand()%3);

            for( int j=i-1; j > 0; j--){
                if( Block_equals(&blocks[i], &blocks[j]) ){
                    unique = 0;
                    break;
                }
            }
        }
    }
    int result = TrackData_test(blocks, RANDOM_TEST_SIZE);

    if( result )
        printf("\nTrackData_Test_random: SUCCESS\n");
    else
        printf("\nTrackData_Test_random: FAILED\n");

    return result;

}

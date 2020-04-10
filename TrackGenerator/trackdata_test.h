//
// Created by malte on 10-Apr-20.
//

#ifndef TRACKGENERATOR_TRACKDATA_TEST_H_H
#define TRACKGENERATOR_TRACKDATA_TEST_H_H

#include "trackdata.h"

/*
 * testBlocks: An array of blocks to test on (make sure, there are not duplicates)
 * numBlocks: Size of the array of Blocks to test
 * returns: Test fails if 0 is returned, otherwise it succeeded
 */
int TrackData_test(Block* testBlocks, int numBlocks);

int TrackData_Test_simple();
int TrackData_Test_random();

#endif //TRACKGENERATOR_TRACKDATA_TEST_H_H

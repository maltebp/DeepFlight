#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>
#include <time.h>

#include "model/track.h"
#include "generation/generation.h"
#include "generation/trackdata.h"
#include "generation/trackdata_test.h"

#define TRACK_FILE_EXT ".dftbd"
#define ARG_COUNT 8


void createFullPath(char* directoryName, char* fileName, char* destination);
void parseGenParameters(char** args, GenParams* params);

int main(int argc, char** argv) {

    // Activate instant printing
    setbuf(stdout, NULL);

    printf("Starting TrackGenerator!\n\n");

    // Decode Arguments
    if( argc != (ARG_COUNT+1) ){
        printf("ERROR: TrackGenerator takes exactly %d arguments but got %d. Correct format is: \n", ARG_COUNT, argc-1);
        printf("'./TrackGenerator <destination folder> <filename> <seed> <length> <stretch> <curve> <width> <width noise>'");
        exit(-1);
    }

    char destinationPath[254] = {0};
    unsigned long int seed = 0;
    GenParams genParams = {0};

    createFullPath(argv[1], argv[2], destinationPath);

    seed = strtoul(argv[3], NULL, 10);
    if(seed == 0){
        printf("ERROR: %s is invalid value for seed\n", argv[3]);
        exit(-1);
    }

    parseGenParameters(argv+4, &genParams);

    printf("Recieved arguments:\n");
    printf("  Full Path: '%s'\n", destinationPath);
    printf("  Seed: %lu\n", seed);
    printf("  Length factor: %d\n", genParams.lengthFactor);
    printf("  Stretch factor: %d\n", genParams.stretchFactor);
    printf("  Length factor: %d\n", genParams.curveFactor);
    printf("  Width factor: %d\n", genParams.widthFactor);
    printf("  Width noise: %d\n", genParams.widthNoise);

    // Generate Track
    Track* track =  generateTrack(&genParams, seed);
    Track_saveToFile(track, destinationPath);
    Track_free(track);

    printf("\nTrack Generator finished successfully!\n");
    return 0;
}


void createFullPath(char* directory, char* fileName, char* destination){
    // Check if it's current directory
    if( directory[0] != '.'){
        // Append destination
        strcat(destination, directory);

        // Append backslash if necessary
        char lastChar = *(destination+strlen(destination)-1);
        if( lastChar != '\\' ){
            strcat(destination, "\\");
        }
    }

    // Append filename + extension
    strcat(destination, fileName);
    strcat(destination, TRACK_FILE_EXT);
}

void parseGenParameters(char** args, GenParams* params){

    params->lengthFactor = strtol(args[0], NULL, 10);
    if( !params->lengthFactor ){
        printf("ERROR: '%s' is not valid value for length factor\n", args[0]);
        exit(-1);
    }

    params->stretchFactor = strtol(args[1], NULL, 10);
    if( !params->stretchFactor ){
        printf("ERROR: '%s' is not valid value for stretch factor\n", args[1]);
        exit(-1);
    }

    params->curveFactor = strtol(args[2], NULL, 10);
    if( !params->curveFactor ){
        printf("ERROR: '%s' is not valid value for curve factor\n", args[2]);
        exit(-1);
    }

    params->widthFactor = strtol(args[3], NULL, 10);
    if( !params->widthFactor ){
        printf("ERROR: '%s' is not valid value for width factor\n", args[3]);
        exit(-1);
    }

    params->widthNoise = strtol(args[4], NULL, 10);
    if( !params->widthNoise ){
        printf("ERROR: '%s' is not valid value for width noise factor\n", args[4]);
        exit(-1);
    }
}

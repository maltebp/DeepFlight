#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <dirent.h>

#include "model/planet.h"
#include "model/track.h"
#include "generation/generation.h"
#include "generation/trackdata.h"
#include "generation/trackdata_test.h"

#define TRACK_FILE_EXT ".dft"

int generateLocalTrack(char* targetFolder, int planetIndex);
void setupLocalPlanetStats(Planet *planets);


int main(int argc, char** argv) {

    printf("Starting TrackGenerator");

    if( argc > 2 ){
        // Arg 1: Track target path
        // Arg 2: Local planet index
        int planetIndex = -1;
        if( argc > 3 ){
            planetIndex = atoi(argv[2]);
        }
        // Generate Local Track
        return generateLocalTrack(argv[1], planetIndex);
    }else{
        // Start auto generation
        printf("ERROR: Generation for database not setup yet!\n");
    }


    return 0;
}


/**
 * Generates a random Track using one of the local Planets,
 * and saves the track file in the specified target folder
 *
 * @param targetFolder   Complete path to folder in which to store the track
 * @param planetIndex   Index of the planet (ranges from -1 to 3, where -1 is random index)
 */
int generateLocalTrack(char* targetFolder, int planetIndex){
    printf("\nGenerating local track\n");
    printf("\nTarget folder: '%s'\n", targetFolder);

    // Check if target folder exists
    DIR *dir = opendir(targetFolder);
    if( !dir ){
        printf("ERROR: Can't find or open the target folder\n");
        return -1;
    }
    closedir(dir);


    Planet localPlanets[4] = {
            {1, "Smar", {184, 53, 9}},
            {2, "Lupto", {24, 146, 171}},
            {3, "Aerth", {49, 102, 44}},
            {4, "Turnsa", {186, 185, 141}}
    };
    int numPlanets = sizeof(localPlanets) / sizeof(Planet);
    setupLocalPlanetStats(localPlanets);

    Planet *planet;
    if( planetIndex == -1 ){
        // Select random planet
        planet = &localPlanets[rand()%numPlanets];
    }else{
        // User specified planet
        // CHeck if planet index is alright
        if( planetIndex > sizeof(localPlanets)/sizeof(Planet) ){
            printf("Local planet index is out of bounds!");
            return -1;
        }
        planet = &localPlanets[numPlanets];
    }

    printf("Planet: ");
    Planet_print(planet);

    Track* track = generateTrack(planet, NULL);

    // Create Full Path
    char fullPath[200] = {0};
    strcat(fullPath, targetFolder);

    // Append backslash if necessary
    char lastChar = *(fullPath+strlen(fullPath)-1);
    if( lastChar != '\\' ){
        strcat(fullPath, "\\");
    }

    strcat(fullPath, track->planet->name);
    strcat(fullPath, "_");
    strcat(fullPath, track->name);
    strcat(fullPath, TRACK_FILE_EXT);

    printf("\nFinal path: '%s'\n", fullPath);

    Track_saveToFile(track, fullPath);
    Track_free(track);

    printf("\nGenerating local track: Success!");
    return 0;
}



/**
 * Setup the generation factors for
 * the 4 planets.
 */
void setupLocalPlanetStats(Planet *planets){
    Planet* planet;

    // Smar (Mars)
    planet = &planets[0];
    planet->lengthFactor    = 15;

    planet->widthFactor     = 10;
    planet->widthNoise      = 20;
    planet->curveFactor     = 20;
    planet ->stretchFactor  = 8;

    // Aerth (Earth)
    planet = &planets[1];
    planet->lengthFactor    = 10;
    planet->widthFactor     = 10;
    planet->widthNoise      = 10;
    planet->curveFactor     = 10;
    planet ->stretchFactor  = 10;

    // Turnsa (Saturn)
    planet = &planets[2];
    planet->lengthFactor    = 25;
    planet->widthFactor     = 20;
    planet->widthNoise      = 10;
    planet->curveFactor     = 8;
    planet ->stretchFactor  = 15;

    // Lupto (Pluto)
    planet = &planets[3];
    planet->lengthFactor    = 15;
    planet->widthFactor     = 12;
    planet->widthNoise      = 2;
    planet->curveFactor     = 20;
    planet ->stretchFactor  = 20;
}

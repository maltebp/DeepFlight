#include <stdio.h>
#include <string.h>

#include "planet.h"
#include "track.h"
#include "generation.h"

int main() {
    Planet planet;
    strcpy(planet.name, "Test Planet");


    Track* track = generateTrack(&planet, NULL);

    printf("\n\nGenerated track");
    Track_print(track);

    return 0;
}


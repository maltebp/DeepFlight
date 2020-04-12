//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_PLANET_H
#define TRACKGENERATOR_PLANET_H

#include <stdint.h>

typedef struct Planet {
    int64_t id;
    char name[100];
    unsigned char color[3]; // rgba

    // How long tracks are
    int lengthFactor;

    //
    int curveFactor;

    // How far between different segments
    int stretchFactor;

    // How wide the tracks are
    int widthFactor;

    // How much the width varies
    int widthNoise;

} Planet;

void Planet_print(Planet* this);


#endif //TRACKGENERATOR_PLANET_H

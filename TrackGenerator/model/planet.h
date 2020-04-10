//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_PLANET_H
#define TRACKGENERATOR_PLANET_H


typedef struct Planet {
    long id;
    char name[100];
    char color[4]; // rgba

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


#endif //TRACKGENERATOR_PLANET_H

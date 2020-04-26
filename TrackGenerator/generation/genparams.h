//
// Created by malte on 21-Apr-20.
//

#ifndef TRACKGENERATOR_GENPARAMS_H
#define TRACKGENERATOR_GENPARAMS_H

typedef struct GenParams {

    // How long tracks are
    int lengthFactor;

    // How much the track will curve
    int curveFactor;

    // How far between different segments
    int stretchFactor;

    // How wide the tracks are
    int widthFactor;

    // How much the width varies
    int widthNoise;

} GenParams;

#endif //TRACKGENERATOR_GENPARAMS_H

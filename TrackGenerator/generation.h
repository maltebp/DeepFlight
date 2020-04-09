//
// Created by malte on 09-Apr-20.
//

#ifndef TRACKGENERATOR_GENERATOR_H
#define TRACKGENERATOR_GENERATOR_H

#include "planet.h"
#include "track.h"

Track* generateTrackFromSeed(Planet *planet, TrackList *existingTracks, long seed);

Track* generateTrack(Planet *planet, TrackList *existingTracks);

#endif //TRACKGENERATOR_GENERATOR_H

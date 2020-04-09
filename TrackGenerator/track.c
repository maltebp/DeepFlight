//
// Created by malte on 09-Apr-20.
//

#include <stdio.h>

#include "track.h"

void Track_print(Track *track){
    printf(
            "Track( id=%ld, name='%s', planet='%s', seed=%ld, )",
            track->id, track->name, track->planet->name, track->seed
    );
}


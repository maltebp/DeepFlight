
#include <stdio.h>

#include "planet.h"



void Planet_print(Planet* planet){
    printf("Planet( id=%lld, name=%s, color=(%d,%d,%d) )\n",
                    planet->id, planet->name, planet->color[0], planet->color[1], planet->color[2] );
}
cmake_minimum_required(VERSION 3.13)
project(TrackGenerator)

set(CMAKE_CXX_STANDARD 14)

add_executable(TrackGenerator main.c model/track.h generation/generation.h generation/generation.c model/track.c generation/trackdata.h generation/trackdata.c generation/trackdata_test.h generation/trackdata_test.c generation/genparams.h)
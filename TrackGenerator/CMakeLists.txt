cmake_minimum_required(VERSION 3.13)
project(TrackGenerator)

set(CMAKE_CXX_STANDARD 14)

add_executable(TrackGenerator main.c track.h generation.h planet.h dbconnection.h dbconnection.c generation.c track.c)
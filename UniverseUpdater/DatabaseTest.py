from source.DatabaseController import *

import json
import bson

f=open("testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()


def test_addAndReturnTrack():
    print("Running test: test_addAndReturnTrack()")
    testTrack = Track("Padborg_test", 999, "11", "33min", num)
    add_TrackObject(testTrack)
    print("Original track: " + testTrack.__str__())
    returnTrackList = get_tracksObjectsList()
    for i in returnTrackList:
        print("Return track: " + i.__str__())
    ##Cleaningup and removing testTrack and trackdata
    removeTrack_andTrackdata(testTrack)


test_addAndReturnTrack()


from source.DatabaseController import *
from source.databaseDAO import *
import json
import bson

f=open("testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()


testTrack = Track(2,"Padborg_test",999,"11","33min",num)
print("Original track: "+testTrack.__str__())
returnTrackList = get_tracksObjectsList()
for i in returnTrackList:
    print("Return track: "+i.__str__())
##add_track(testTrack)







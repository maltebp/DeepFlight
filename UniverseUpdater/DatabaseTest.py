from source.track import Track
from source.database import *
import json
import bson

f=open("testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()

testTrack = Track(99,10000,"Padborg_test",999)

x=json.dumps(testTrack.__dict__)
get_tracks_data()
print(x)
add_track(testTrack.__dict__,num)
get_trackdata()

for x in get_tracks():
    print(get_trackdata(x))

for x in get_trackdata():
    print(x)


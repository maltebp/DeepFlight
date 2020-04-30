

import binascii
import subprocess
from source.track import Track

from source import database

#Execute TrackGeneration.c and creating a Track
#subprocess.call(["TrackGenerator.exe", ".", "testtrack","123", "10", "10", "10", "10", "10"])



import string
import random
import subprocess
import time

from source.planet import Planet



__TRACK_GENERATOR_PATH = "../TrackGenerator.exe"
__TRACK_FILE_NAME = "generatedtrack"
__TRACK_FILE_EXT = ".dftbd"

#
# import subprocess
# argList = ["testtrack","123", "10", "10", "10", "10", "10"]
# #Execute TrackGeneration.c and creating a Track
# subprocess.call(["TrackGenerator.exe", ".", "testtrack","123", "10", "10", "10", "10", "10"])
#
#
# #Open the created track file ex. testtrack.exe
# f=open("testtrack.dftbd","rb")
# num=bytearray(f.read())
# print (num)
# f.close()
# with open("testtrack.dftbd", mode='rb') as file: # b is important -> binary
#     fileContent = file.read()
# #Printing the length of the track (in byts)
# print(len(num))
#
#

def generateTrack(planet, existingTracks):

    name = generateUniqueTrackName(existingTracks)

    # Using current milliseconds as seed
    seed = int(round(time.time() * 1000))
    print(str(seed))

    # Start the Track generator
    subprocess.call([
        __TRACK_GENERATOR_PATH, ".",
        __TRACK_FILE_NAME,
        str(seed),
        str(planet.lengthFactor),
        str(planet.stretchFactor),
        str(planet.curveFactor),
        str(planet.widthFactor),
        str(planet.widthNoiseFactor)
    ])

    # Open the created track file
    file = open(__TRACK_FILE_NAME + __TRACK_FILE_EXT, "rb")
    data = bytearray(file.read())

    print(f"Read {len(data)} bytes")






# Generates a Track name which is not in use
# among the given existing tracks
def generateUniqueTrackName(existingTracks):
    name = ""
    nameExists = True

    # Generate name
    while nameExists:
        name = generateTrackName()
        nameExists = False

        for track in existingTracks:
            if (track.name == name):
                nameExists = True



# Generates a Track name in the format
#   AAAA-000
def generateTrackName():
    name = ""
    for x in range(4):
        name += random.choice(string.ascii_uppercase)
    name += "-"
    for x in range(3):
        name += str(random.randint(0, 9))
    return name


#Open the created track file ex. testtrack.exe
f=open("../testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()
with open("../testtrack.dftbd", mode='rb') as file: # b is important -> binary
   fileContent = file.read()
#Printing the length of the track (in byts)
print(len(num))




trackObject = Track(48534895,2,999);
trackObject.setData(num)
#trackObject.setData(num)
database.add_track(trackObject)

trackList = database.get_tracks()

for track in trackList:
      print(track.__str__())






#binary_field = num
#print(binascii.hexlify(binary_field))



print(f'Hey my name is {name}.')

generateTrack(Planet(10, "Test Planet", [100,150,120], 10, 10, 10, 10, 10), [] )


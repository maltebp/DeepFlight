
import string
import random
import subprocess
import time

from source.planet import Planet
from source.track import Track



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

# Generate a Track from the generation factors of a Planet,
# and a list of existing tracks (for unique generation)
def generateTrack(planet, existingTracks):

    # Generate a cool name
    name = generateUniqueTrackName(existingTracks)
    print(f"Generated Name {name}")

    # Using current milliseconds as seed
    seed = generateUniqueSeed(existingTracks)
    print(f"Generated Seed: {seed}")

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
    file.close()

    return Track(0, name, planet.id, data, seed)


# Generates a random seed which fits in 4 bytes (required by TrackGenerator.exe)
def generateUniqueSeed(existingTracks):
    seed = 0
    seedExists = True
    while seedExists:
        # Generate random seed which fits in 4 bytes
        seed = random.randint(0, 2147483646)
        seedExists = False
        for track in existingTracks:
            if track.seed == seed:
                seedExists = True

    return seed



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
    return name


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



track = generateTrack(Planet(10, "Test Planet", [100,150,120], 10, 10, 10, 10, 10), [] )
print(f"Generated Track: {track}")
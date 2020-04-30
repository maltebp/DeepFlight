
import binascii
import subprocess
from source.track import Track

from source import database

#Execute TrackGeneration.c and creating a Track
#subprocess.call(["TrackGenerator.exe", ".", "testtrack","123", "10", "10", "10", "10", "10"])




#Open the created track file ex. testtrack.exe
f=open("../testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()
with open("../testtrack.dftbd", mode='rb') as file: # b is important -> binary
   fileContent = file.read()
#Printing the length of the track (in byts)
print(len(num))

name = 'Hello'

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
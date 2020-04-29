

import subprocess
argList = ["testtrack","123", "10", "10", "10", "10", "10"]
#Execute TrackGeneration.c and creating a Track
subprocess.call(["TrackGenerator.exe", ".", "testtrack","123", "10", "10", "10", "10", "10"])


#Open the created track file ex. testtrack.exe
f=open("testtrack.dftbd","rb")
num=bytearray(f.read())
print (num)
f.close()
with open("testtrack.dftbd", mode='rb') as file: # b is important -> binary
    fileContent = file.read()
#Printing the length of the track (in byts)
print(len(num))

name = 'Hello'

print(f'Hey my name is {name}.')
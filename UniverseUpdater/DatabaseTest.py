from source.Database.DatabaseController import *
from source.model.track import *
import time

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


def test_addRound():
    print("Running test: test_addRound()")
    startDate = int(round(time.time() * 1000))
    endDate = int(round(time.time() * 1000)+100000)

    trackIds=[1,2,3,4]

    rankings=[]
    ranking1 = Ranking(1,66,55)
    ranking2 = Ranking(2, 44, 33)
    rankings.append(ranking1)
    rankings.append(ranking2)
    testRound = Round(trackIds,2,startDate,endDate,rankings)
    add_roundObject(testRound)
    removeRound(testRound)

test_addRound()
#test_addAndReturnTrack()


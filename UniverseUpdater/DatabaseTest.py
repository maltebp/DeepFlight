from source.Database.DatabaseController import *
from source.model.track import *
import time

f=open("testtrack.dftbd", "rb")
num=bytearray(f.read())
print (num)
f.close()


def test_addAndReturnTrack():
    print("Running test: test_addAndReturnTrack()")
    testTrack = Track("Flensborg_test", 888, "11", "33min", num)
    testTrack.setId(add_TrackObject(testTrack))
    print("Original track: " + testTrack.__str__())
    returnTrackList = get_tracksObjectsList()
    for i in returnTrackList:
        print("Return track: " + i.__str__())
    ##Cleaningup and removing testTrack and trackdata
    removeTrack_andTrackdata(testTrack)


def test_addRound():
    print("Running test: test_addRound()")

    #Creating round object
    startDate = int(round(time.time() * 1000))
    endDate = int(round(time.time() * 1000)+100000)

    trackIds=[1,2,3,4]

    rankings=[]
    ranking1 = Ranking(1,66,55)
    ranking2 = Ranking(2, 44, 33)
    rankings.append(ranking1)
    rankings.append(ranking2)
    testRound = Round(trackIds,2,startDate,endDate,rankings)
    print("Original round object"+testRound.__str__())
    print(testRound)
    #Add round to database
    testRound.setId(add_roundObject(testRound))
    returnRound = get_roundsObjectList()
    for i in returnRound:
        print("Return rounds: " + i.__str__())
    removeRound(testRound)

def test_add_planet():
    testPlanet = Planet("Mars","blue",10,10,20,100,66)
    testPlanet.setId(add_planetsToDB(testPlanet))
    print("Original planet: "+testPlanet.__str__())
    for i in get_planetObjectList():
        print("Return planet: "+i.__str__())
    removePlanetFromDB(testPlanet)


def test_add_User():
    testUser = User("Per",20,303)
    testUser.setId(add_UserToDB(testUser))
    print("Original user: "+testUser.__str__())
    userList = get_UserObjectList()
    for user in userList:
        print("Return user: "+user.__str__())
    removePlanetFromDB(testUser)

#test_add_User()
#test_add_planet()
#test_addRound()
test_addAndReturnTrack()


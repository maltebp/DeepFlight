
import threading
import time
import pymongo
import bson
import random
from source.updater import universeupdater
from source.Database import databasecontroller
from source.model.user import *


client = pymongo.MongoClient(f"mongodb+srv://game:deepflightisawesome@deepflight-cu0et.mongodb.net/test?retryWrites=true&authSource=admin")
db = client["gamedb_test"]


def insertTime(track, userId, time):
    track.times[str(userId)] = time
    newvalues = {"$set": {"times" : track.times}}
    db["tracks"].update_one({"_id": bson.ObjectId(track._id)}, newvalues)


def createUsers(users):
    print("Creating users: ")
    userIds = {}
    for user in users:
        print(f"\t'{user.username}'... ", end="")
        insertedId = db["users"].insert_one({"username": user.username}).inserted_id
        userIds[user.username] = str(insertedId)
        print(f"Created (id={insertedId})")
    return userIds



def getCurrentTracks():
    currentRound = getCurrentRound()
    currentTrackIds = currentRound.trackIds

    tracks = []
    for track in databasecontroller.get_tracksObjectsList():
        if track._id in currentTrackIds:
            tracks.append(track)

    return tracks


def getCurrentRound():
    rounds = databasecontroller.get_roundsObjectList()
    currentTime = int(round(time.time() * 1000))

    # Check status of each round
    for thisround in rounds:
        # bad iterator name (thisround) is due to 'round' being a function

        if thisround.startDate < currentTime < thisround.endDate:
            return thisround





print("\nSTARTING RANKING TEST!\n")

# Starting Updater on seperate thread
#thread = threading.Thread(target=universeupdater.startUpdater)
#thread.start()


#time.sleep(60)
print("STARTING TIME UPDATE!")

databasecontroller.initializeDatabase(testMode=True)

userIds = createUsers([
    User("Malte"),
    User("Andreas"),
    User("Rasmus"),
    User("Erlend")
])


while True:
    time.sleep(10)
    print("Adding new times:")
    tracks = getCurrentTracks()
    for track in tracks:
        for userName, userId in userIds.items():
            trackTime = random.randint(10, 1000)
            insertTime(track, userId, trackTime)
            print(f"\tUpdated time: track='{track.name}', user={userName}, time={trackTime}")
import pymongo
import bson

from source.track import Track

# Settings
_use_test_database = True
_db_user = "universeupdater"
_db_password = "deepflightisawesome"
_db_name = "game"




# ----------------------------------------------------------------------------------
# Setup database connection

# Establish connection
client = pymongo.MongoClient(f"mongodb+srv://{_db_user}:{_db_password}@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority")
db = client["gamedb" + ("_test" if _use_test_database else "")]

print(f"Collections: {db.list_collection_names()}")



def get_planets():
    return db["planets"].find()




def add_track(track):

    obj = {
        "id" : track.id,
    "name" : track.name,
    "planetId" : track.planetId,
    "data" : bson.Binary(track.data)
    }
    db["tracks"].insert_one(obj).inserted_id
    print("Saving track")

    db_track = {"name": track.name, "planetId": track.planetId}

    # Return value of insert is the _id generated
    _id = db["tracks"].insert_one(db_track).inserted_id
    print("_id" + str(_id))

    if track.data is not None:
        print("Track data is not None")
        db["trackdata"].insert_one({"_id": _id, "data": bson.Binary(track.data)})



def get_track_data():
    data = db["trackdata"].find()
    print("Documents in 'trackdata': " + str(data.count()))

def get_tracks():
    db_tracks = db["tracks"].find()

    # Convert to Track objects
    tracks = []
    for db_track in db_tracks:
        print("a")
        track = Track(db_track["_id"], db_track["name"], db_track["planetId"] )
        tracks.append(track)

    return tracks


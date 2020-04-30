import pymongo
import bson
from source.track import Track

# Settings
_use_test_database = True
_db_user = "universeupdater"
_db_password = "deepflightisawesome"
_db_name = "gamedb"


# ----------------------------------------------------------------------------------
# Setup database connection

# Establish connection
client = pymongo.MongoClient(f"mongodb+srv://{_db_user}:{_db_password}@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority")
db = client["gamedb" + ("_test" if _use_test_database else "")]
print(db.list_collection_names())


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



def get_tracks():
    db_tracks = db["tracks"].find()

    # Convert to Track objects
    tracks = []
    for db_track in db_tracks:
        track = Track(db_track["_id"], db_track["name"], db_track["planetId"] )
        tracks.append(track)

    return tracks


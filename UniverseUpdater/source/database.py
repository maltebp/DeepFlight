import pymongo
import bson
from pymongo import ReadPreference, WriteConcern
from pymongo.read_concern import ReadConcern

from source.model.track import Track

# Settings
_use_test_database = True
_db_user = "universeupdater"
_db_password = "deepflightisawesome"
_db_name = "game"




# ----------------------------------------------------------------------------------
# Setup database connection
# Establish connection
client = pymongo.MongoClient(f"mongodb+srv://{_db_user}:{_db_password}@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority0&authSource=admin")
db = client["gamedb" + ("_test" if _use_test_database else "")]
majority0 = WriteConcern("majority", wtimeout=1000)
print(f"Collections: {db.list_collection_names()}")


#####################GET COLLECTION DATA########################################

def get_planets():
    #return a list with all planets
    print(db["planets"].find())
    return db["planets"].find()

def get_tracks():
    # return a list with all tracks
    return db["tracks"].find()

def get_reunds():
    # return a list with all rounds
    return db["rounds"].find()

def get_trackdata():
    # return a list with all trackdata
    return db["trackdata"].find()









##################ADD DATA TO COLLECTIONS###########################################
##Adding track with transaction.
#If an error accures the transaction will be aborted.
def add_track(track,trackdata):
        with client.start_session() as session:
            with session.start_transaction():
                _id = db["tracks"].insert_one(track,majority0, session=session).inserted_id
                db["trackdata"].insert_one({"_id": _id, "data": bson.Binary(trackdata)}, session=session)



def get_tracks_data():
    data = db["trackdata"].find()
    print("Documents in 'trackdata': " + str(data.count()))


    # Convert to Track objects
   # tracks = []
    #for db_track in data:
     #   print("a")
      #  track = Track(db_track["_id"], db_track["name"], db_track["planetId"] )
       # tracks.append(track)

    #return tracks

def add_track2(db_track):
    db["tracks"].insert_one(db_track,majority0)


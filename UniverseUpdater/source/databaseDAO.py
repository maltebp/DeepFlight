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

#Collections
_db_users = 'user'
_db_planets = 'planets'
_db_tracks = 'tracks'
_db_rounds = 'allrounds'
_db_current_round = 'courrentround'
_db_trackdata ="trackdata"


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
    print(db[_db_planets].find())
    return db[_db_planets].find()

def get_tracks():
    # return a list with all tracks
    return db[_db_tracks].find()

def get_rounds():
    # return a list with all rounds
    return db[_db_rounds].find()

def get_trackdata():
    # return a list with all trackdatadocuments
    return db[_db_trackdata].find()

def get_users():
    # return a list with all users
    return db[_db_users].find()

def get_current_round():
    # return a list of all tracks in the current round
    return db[_db_current_round].find()

def get_single_trackdata(id):
    return db[_db_trackdata].find_one({"_id":id})










##################ADD DATA TO COLLECTIONS###########################################
##Adding track with transaction.
#If an error accures the transaction will be aborted.
def add_track(track):

    db_track = {
        "_id": track._id,
        "name": track.name,
        "planetId": track.planetId,
        "seed":track.seed,
        "times":track.times,
    }
    #Using the same id from track
    db_trackdata = {
        "_id":track._id,
        "trackdata":bson.Binary(track.data)
    }
    with client.start_session() as session:
        with session.start_transaction():
            db[_db_tracks].insert_one(db_track, majority0, session=session)
            db[_db_trackdata].insert_one(db_trackdata, session=session)



    # Convert to Track objects
   # tracks = []
    #for db_track in data:
     #   print("a")
      #  track = Track(db_track["_id"], db_track["name"], db_track["planetId"] )
       # tracks.append(track)

    #return tracks




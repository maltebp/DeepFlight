import pymongo
from source.Database.databaseStartUp import *
import json

# Settings
_use_test_database = True
_db_user = "universeupdater"
_db_password = "deepflightisawesome"
_db_name = "game"

#Collections
df_collections = []
_db_users = 'user'
_db_planets = 'planets'
_db_tracks = 'tracks'
_db_rounds = 'rounds'
_db_trackdata ="trackdata"
df_collections.append(_db_users)
df_collections.append(_db_planets)
df_collections.append(_db_tracks)
df_collections.append(_db_rounds)
df_collections.append(_db_trackdata)
sorted(df_collections)

# ----------------------------------------------------------------------------------
# Setup database connection
# Establish connection
client = pymongo.MongoClient(f"mongodb+srv://{_db_user}:{_db_password}@deepflight-cu0et.mongodb.net/test?retryWrites=true&authSource=admin")
db = client["gamedb" + ("_test" if _use_test_database else "")]
collections =  sorted(db.collection_names())

#Creates a collection and insert default values in the collections if collection is missing
if _use_test_database:
    startUp(db,df_collections,collections)




#####################GET COLLECTION DATA########################################

def get_planets():
    #return a list with all planets
    return db[_db_planets].find()

def get_tracks():
    # return a list with all tracks
    return db[_db_tracks].find()

def get_rounds_DAO():
    # return a list with all rounds
    return db[_db_rounds].find()

def get_trackdata():
    # return a list with all trackdatadocuments
    return db[_db_trackdata].find()

def get_users():
    # return a list with all users
    return db[_db_users].find()

def get_single_trackdata(id):
    return db[_db_trackdata].find_one({"_id":id})

##################ADD DATA TO COLLECTIONS###########################################


##Adding track with transaction.
#If an error accures the transaction will be aborted.
#Add a track/trackdata Json object to database
#This is access to database
def add_track(track):
    db_track = {
        "name": track.name,
        "planetId": track.planetId,
        "seed": track.seed,
        "times": track.times,
    }

    with client.start_session() as session:
        with session.start_transaction():
            trackId = db[_db_tracks].insert_one(db_track, session=session).inserted_id
            # Using the same id from track
            db_trackdata = {
                "_id": trackId,
                "trackdata": bson.Binary(track.data)
            }
            db[_db_trackdata].insert_one(db_trackdata, session=session)
            return trackId


#Add a round Json object to database
#This is access to database
def addRound(round):
    # Converting rankingObjects to a JsonArray
    ranksString = []
    for rank in round.rankings:
        db_rank = {
            "user_id": rank.user_id,
            "rating": rank.rating,
            "rank": rank.rank,
        }
        ranksString.append(db_rank)
    print(ranksString)
    # Converting roundobject to dictionart(json object)
    db_round = {
        "trackId": round.trackId,
        "roundNumber": round.roundNumber,
        "startDate": round.startDate,
        "endDate": round.endDate,
        "rankings": ranksString,
    }
    # Adding jsonobject to database
    return db[_db_rounds].insert_one(db_round).inserted_id


def update_round_DAO(round):
    #Creating list of rankings
    ranksString = []
    for rank in round.rankings:
        db_rank = {
            "user_id": rank.user_id,
            "rating": rank.rating,
            "rank": rank.rank,
        }
        ranksString.append(db_rank)

    myquery = {"_id": round._id}
    newvalues = {"$set": {"trackId": round.trackId,
        "roundNumber": round.roundNumber,
        "startDate": round.startDate,
        "endDate": round.endDate,
        "rankings": ranksString,}}
    db[_db_rounds].update_one(myquery, newvalues)
    return round


#Add a planet to Planet collection
#Return 1 on success and 0 on failure
def addPlanet(planet):
    # Converting planetobject to dictionart(json object)
    db_planet = {
        "name": planet.name,
        "color": planet.color,
        "lengthFactor": planet.lengthFactor,
        "curveFactor": planet.curveFactor,
        "stretchFactor": planet.stretchFactor,
        "widthFactor": planet.widthFactor,
        "widthNoiseFactor": planet.widthNoiseFactor,
    }
    return db[_db_planets].insert_one(db_planet).inserted_id




#Add a user to User collection
#Return 1 on success and 0 on failure
def addUser(user):
    db_user = {
        "username": user.username,
        "rank": user.rank,
        "rating": user.rating,
    }
    return db[_db_users].insert_one(db_user).inserted_id



def updateUserDAO(user):
    myquery = {"_id": user._id}
    newvalues = {"$set": {"rank": user.rank, "rating":user.rating}}
    db[_db_users].update_one(myquery, newvalues)
    return user


###################################REMOVE FROMDATABASE############################################

#Remove a track from tracks collection
def removeTrack_andTrackdata(track):
    print("TrackId to remove :"+str(track._id))
    db[_db_tracks].remove({"_id":track._id})
    db[_db_trackdata].remove({"_id":track._id})

#Remove a round from collection rounds.
def removeRound(round):
    print("roundId to remove :" + str(round._id))
    db[_db_rounds].remove({"_id":round._id})

def removePlanetFromDB(planet):
    print("planetId to remove :" + str(planet._id))
    db[_db_planets].remove({"_id": planet._id})


# Remove a user from collection rounds.
def removeUserFormUserCollection(user):
    print("UserId to remove :" + str(user._id))
    db[_db_planets].remove({"_id": user._id})







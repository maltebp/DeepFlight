import pymongo
import bson

# Settings
_db_user = "universeupdater"
_db_password = "deepflightisawesome"
_db_name = "game"

#Collections
df_collections = []
_db_users = 'users'
_db_planets = 'planets'
_db_tracks = 'tracks'
_db_rounds = 'rounds'
_db_trackdata ="trackdata"
df_collections.append(_db_users)
df_collections.append(_db_planets)
df_collections.append(_db_tracks)
df_collections.append(_db_rounds)
df_collections.append(_db_trackdata)

# ----------------------------------------------------------------------------------
# Setup database connection
# Establish connection
client = pymongo.MongoClient(f"mongodb+srv://{_db_user}:{_db_password}@deepflight-cu0et.mongodb.net/test?retryWrites=true&authSource=admin")
db = None


def initializeDatabase(testMode=False, clearDatabase=False):
    print("\nInitializing Database")
    print(f"\tTest mode: {testMode}")
    print(f"\tClear database: {clearDatabase}")

    db_name = "gamedb" + ("_test" if testMode else "")
    global db
    db = client[db_name]
    print(f"\tDatabase name: {db_name}")

    print("Setting up collections:")
    collections = db.collection_names()
    for df_collection in df_collections:
        print(f"\t'{df_collection}': ", end="")
        if not df_collection in collections:
            db.create_collection(df_collection)
            print("Created")
        elif clearDatabase:
            db.drop_collection(df_collection)
            db.create_collection(df_collection)
            print("Cleared")
        else:
            print("Exists")
    print("Database initialized!")



# Initializes the database, by ensuring collections exists
# This is required since collections cannot be created during mutli-doc transactions



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
    return db[_db_trackdata].find_one({"_id": bson.ObjectId(id)})

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
                "data": bson.Binary(track.data)
            }
            db[_db_trackdata].insert_one(db_trackdata, session=session)
            return str(trackId)


#Add a round Json object to database
#This is access to database
def addRound(round):
    # Converting roundobject to dictionart(json object)
    db_round = {
        "trackIds": round.trackIds,
        "roundNumber": round.roundNumber,
        "startDate": round.startDate,
        "endDate": round.endDate,
    }

    # Converting rankingObjects to a JsonArray
    if round.rankings is not None:
        db_round["rankings"] = round.rankings

    # Adding jsonobject to database
    roundId = db[_db_rounds].insert_one(db_round).inserted_id
    return str(roundId)


def update_round_DAO(round):
    # Creating list of rankings
    myquery = {"_id": bson.ObjectId(round._id) }

    newValues = {
        "trackIds": round.trackIds,
        "roundNumber": round.roundNumber,
        "startDate": round.startDate,
        "endDate": round.endDate,
    }
    if round.rankings is not None:
        newValues["rankings"] = round.rankings

    db[_db_rounds].update_one(myquery, {"$set": newValues})
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
    return str(db[_db_planets].insert_one(db_planet).inserted_id)




#Add a user to User collection
#Return 1 on success and 0 on failure
def addUser(user):
    db_user = {
        "username": user.username,
        "rank": user.rank,
        "rating": user.rating,
    }
    return str(db[_db_users].insert_one(db_user).inserted_id)



def updateUserDAO(user):
    myquery = {"_id": bson.ObjectId(user._id) }
    newvalues = {"$set": {"rank": user.rank, "rating": user.rating}}
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





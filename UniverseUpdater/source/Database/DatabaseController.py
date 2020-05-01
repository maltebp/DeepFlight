from source.Database.databaseDAO import *
from source.model.planet import Planet
from source.model.track import *
from source.model.round import *
from source.model.ranking import *

#Returns a list of all tracks in the database
from source.model.user import User


def get_tracksObjectsList():
    trackDocuments = get_tracks()
    # Convert to Track objects
    tracks = []
    for trackDocument in trackDocuments:
        track = Track(trackDocument["name"], trackDocument["planetId"],trackDocument["seed"],trackDocument["times"],get_single_trackdata(int(trackDocument["_id"]))["trackdata"])
        track.setId(int(trackDocument["_id"]))
        tracks.append(track)
    #Returning track object list
    return tracks


#Adds a track to the database
#Returns the id for the track on success
def add_TrackObject(track):
    newId = int(get_tracks().count())+1
    #Adding one to get at new id
    track.setId(newId)
    success = add_track(track)
    if success is 0:
        print("Failed to add track")
        return 0
    else:
        print("Track is added to database")
        return newId


#Add a round object to database
#Returns the id for the round on success
def add_roundObject(round):
    newId = int(get_rounds_DAO().count())+1
    round.setId(newId)
    print('Adding new round. Round id: '+str(newId))
    success = addRound(round)
    if success is 0:
        print("Failed to add round")
        return 0
    else:
        print("Round is added to database")
        return newId


#Returns a list of all rounds in the database
def get_roundsObjectList():
    rounds = get_rounds_DAO()
    roundObjectList = []
    #Creating Roundobjects from datatabase
    for round in rounds:
        rankings = []
        #Fetching ranks from database and creating rankingobjects
        for rank in round['rankings']:
            user_id = int(rank["user_id"])
            rating = int(rank["rating"])
            rank = int(rank["rank"])
            rankObjekt = Ranking(user_id,rating,rank)
            rankings.append(rankObjekt)
        roundObjekt = Round(round["trackId"],round["roundNumber"],round["startDate"],round["endDate"],rankings)
        roundObjekt.setId(int(round["_id"]))
        roundObjectList.append(round)
    #Returning a list of round objects
    return roundObjectList


#Add planets
#Returns the id for the planet on success
def add_planetsToDB(planet):
    newId = int(get_planets().count()) + 1
    planet.setId(newId)
    print('Adding new planet. Planet id: ' + str(newId))
    success = addPlanet(planet)
    if success is 0:
        print("Failed to add planet")
        return 0
    else:
        print("Planet is added to database")
        return newId


def get_planetObjectList():
        planetDocuments = get_planets()
        # Convert to Track objects
        planets = []
        for planet in planetDocuments:
            planetObject = Planet(planet["name"], planet["color"], planet["lengthFactor"], planet["curveFactor"], planet["stretchFactor"], planet["widthFactor"], planet["widthNoiseFactor"])
            planetObject.setId(planet["_id"])
            planets.append(planetObject)
        # Returning track object list
        return planets


#Add users
#Returns the id for the user on success
def add_UserToDB(user):
    newId = int(get_planets().count()) + 1
    user.setId(newId)
    print('Adding new user. User id: ' + str(newId))
    success = addUser(user)
    if success is 0:
        print("Failed to add planet")
        return 0
    else:
        print("Planet is added to database")
        return newId


#Returns a list of all users in the database
def get_UserObjectList():
    userDocuments = get_users()
    # Convert to Track objects
    users = []
    for user in userDocuments:
        userObject = User(user["username"], user["rank"], user["rating"])
        userObject.setId(user["_id"])
        users.append(userObject)
    # Returning track object list
    return users



def printDocuments(documents):
    for i in documents:
        print(i)




from source.Database.databasedao import *
from source.model.planet import Planet
from source.model.track import *
from source.model.round import *
from source.model.ranking import *


#Returns a list of all tracks in the database
from source.model.user import User

#Returns a list of track objects
def get_tracksObjectsList():
    trackDocuments = get_tracks()
    # Convert to Track objects
    tracks = []
    for trackDocument in trackDocuments:
        track = Track(trackDocument["name"], trackDocument["planetId"], trackDocument["seed"], trackDocument["times"], get_single_trackdata(trackDocument["_id"])["data"])
        track.setId(trackDocument["_id"])
        tracks.append(track)
    #Returning track object list
    return tracks


#Adds a track to the database
#Returns the track object with _id from mongoDB
def add_TrackObject(track):
    #Adding one to get at new id
    track.setId(add_track(track))
    return track


#Add a round object to database
#Returns the round object with _id from mongoDB
def add_roundObject(round):
    newId = addRound(round)
    round.setId(newId)
    return round


#Returns a list of all roundsObjects in the database
def get_roundsObjectList():
    rounds = get_rounds_DAO()
    roundObjectList = []
    #Creating Roundobjects from datatabase
    for round in rounds:

        roundObjekt = Round(round["roundNumber"], round["trackIds"], round["startDate"],round["endDate"])
        if "rankings" in round:
            roundObjekt.rankings = round["rankings"]

        roundObjekt.setId(round["_id"])
        roundObjectList.append(roundObjekt)
    #Returning a list of round objects
    return roundObjectList

#Returns the updated round object
def update_round(round):
    round = update_round_DAO(round)
    return round

#Add planets
#Returns the Planet object with _id from mongoDB
def add_planetsToDB(planet):
    newId = addPlanet(planet)
    planet.setId(newId)
    return planet

#Returns a list of all planetObjects in the database
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
#Returns the user opject with the userid set
def add_UserToDB(user):
    userId = addUser(user)
    print('New User added to database. User id: ' + str(userId))
    user.setId(userId)
    return user



#Returns a list of all users objects in the database
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

#Updates an existing user with new user informations
#Returns the updated userobject
def updateUser(user):
    user = updateUserDAO(user)
    return user

def printDocuments(documents):
    for i in documents:
        print(i)




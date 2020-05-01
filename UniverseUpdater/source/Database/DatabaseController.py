from source.Database.databaseDAO import *
from source.model.track import *
from source.model.round import *
from source.model.ranking import *


def get_tracksObjectsList():
    trackDocuments = get_tracks()
    print("Documents in 'tracks': " + str(trackDocuments.count()))
    # Convert to Track objects
    tracks = []
    for trackDocument in trackDocuments:
        track = Track(trackDocument["name"], trackDocument["planetId"],trackDocument["seed"],trackDocument["times"],get_single_trackdata(int(trackDocument["_id"]))["trackdata"])
        track.setId(int(trackDocument["_id"]))
        tracks.append(track)
    return tracks

def add_TrackObject(track):
    newId = int(get_tracks().count())
    #Adding one to get at new id
    track._id=newId+1
    success = add_track(track)
    if success is 0:
        print("Failed to add track")
    else:
        print("Track is added to database")



def add_roundObject(round):
    newId = int(get_rounds_DAO().count())
    round.setId(newId)
    success = addRound(round)
    if success is 0:
        print("Failed to add round")
    else:
        print("Track is added to database")


def get_rounds():
    rounds = get_rounds_DAO()
    roundObjectList = []
    for round in rounds:
        print(round)
        rankings = []
        for rank in round['rankings']:
            print(rank)
            rankObjekt = Ranking(int(rank["user_id"]),int(rank['rating']),int(rank["rank"]))
            print(rank)
            rankings.append(rankObjekt)
        roundObjekt = Round(round["trackIds"],round["roundNumber"],round["startDate"],round["endDate"],rankings)
        roundObjekt.setId(int(round["_id"]))
    roundObjectList.append(round)


def printDocuments(documents):
    for i in documents:
        print(i)




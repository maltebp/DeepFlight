from source.Database.databaseDAO import *
from source.model.track import *


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


#


def printDocuments(documents):
    for i in documents:
        print(i)




from source.databaseDAO import *


def get_tracksObjectsList():
    trackDocuments = get_tracks()
    print("Documents in 'tracks': " + str(trackDocuments.count()))
    # Convert to Track objects
    tracks = []
    for trackDocument in trackDocuments:
        track = Track(int(trackDocument["_id"]), trackDocument["name"], trackDocument["planetId"],trackDocument["seed"],trackDocument["times"],get_single_trackdata(int(trackDocument["_id"]))["trackdata"])
        tracks.append(track)
    return tracks

def printDocuments(documents):
    for i in documents:
        print(i)
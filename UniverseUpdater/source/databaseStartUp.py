import bson

# This method is only used if collections "tracts" or "trackdata" does not exist.
# It is not possible to insert with transactions if a collection does not exist

def startUp(db,df_collections,collections):
    for i in collections:
        for j in df_collections:
            if collections.__contains__(j):
                print(i)
            else:
                if j == 'tracks' or j=='trackdata':
                    add_single_track(db)



def add_single_track(db):
    try:
        print('Adding single track')
        db_track = {
            "_id": 1,
            "name": "test",
            "planetId": "testplanet",
            "seed": 33,
            "times": "testtime",
        }
        # Using the same id from track
        f = open("testtrack.dftbd", "rb")
        num = bytearray(f.read())
        print(num)
        f.close()
        db_trackdata = {
            "_id": 1,
            "trackdata": bson.Binary(num)
        }

        db['tracks'].insert_one(db_track)
        # Creating trackdata
        db['trackdata'].insert_one(db_trackdata)
    except:
        return
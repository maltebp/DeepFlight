import bson
import json

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
                elif j=="rounds":
                    add_single_round(db)

def add_single_track(db):
    #try:
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
            "data": bson.Binary(num)
        }

        db['tracks'].insert_one(db_track)
        # Creating trackdata
        db['trackdata'].insert_one(db_trackdata)
    # except Exception as err:
    #



#Adding a random round to the database
def add_single_round(db):
    #try:
        print('Adding single round')
        db_round = {
            "_id": 1,
            "trackId": [1,2,3,4],
            "roundNumber": 1,
            "startDate": 1588079852194,
            "endDate":1588166252194,
            "rankings":[{"user_id":1, "rating":88,"rank":5},{"user_id":2, "rating":96,"rank":7}],
        }
        db['rounds'].insert_one(db_round)
    # except Exception as err:
    #     print(err)

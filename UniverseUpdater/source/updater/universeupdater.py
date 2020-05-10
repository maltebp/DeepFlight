
import time
import timeit # Used to measure run time
from source.updater import ranker, generate
from source.model.round import Round
from source.model.user import User
from source.Database import databasecontroller
from source.model.planet import *
from prometheus_client import start_http_server

start_http_server(8000)

_UPDATE_FREQUENCY = 30 # seconds
_ROUND_LENGTH = 10 # Minutes
_CLEAR_DATABSE_ON_START = False
_TEST_MODE = False

# Number of rounds to base the universal ranking upon
_UNIVERSAL_RANKING_ROUND_COUNT  = 3


def updateUniverse():

    print("\nUpdating Universe...")
    startTime = timeit.default_timer()

    rounds = databasecontroller.get_roundsObjectList()

    currentRound = None
    unrankedRounds = []
    futureRounds = []
    rankedRounds = []

    currentTime = int(round(time.time() * 1000))

    # Check status of each round
    for thisround in rounds:
        # bad iterator name (thisround) is due to 'round' being a function

        if thisround.startDate < currentTime < thisround.endDate:
            if currentRound is not None:
                print("WARNING: More than 1 current round!")
            currentRound = thisround

        if thisround.endDate < currentTime:
            if thisround.rankings is not None:
                rankedRounds.append(thisround)
            else:
                unrankedRounds.append(thisround)

        if thisround.startDate > currentTime:
            futureRounds.append(thisround)

    print("Current round state:")
    print(f"\tTotal Rounds:     {len(rounds)}")
    print(f"\tCurrent Round:    {currentRound}")
    print(f"\tUnranked Rounds:  {len(unrankedRounds)}")
    print(f"\tFuture Rounds:    {len(futureRounds)}")

    # Update the universe according to round states
    if currentRound is None:
        print("\nCreating new current round")
        # No current round, so we create one
        currentRound = createRound(currentTime)
        print("Created current round: ", currentRound)

    if len(futureRounds) is 0:
        print("\nCreating future round")
        # No future round, so we create on
        createdRound = createRound(currentRound.endDate)
        print("Created future round: ", createdRound)

    if len(unrankedRounds) > 0:
        print("\nUpdating rankings")
        # Unranked rounds, so we rank them (also updates universe rankings)
        allTracks = databasecontroller.get_tracksObjectsList()
        updateRankings(rankedRounds, unrankedRounds, allTracks)
        print("Finished updating rankings")

    endTime = timeit.default_timer()
    runTime = endTime - startTime
    print(f"Finished update in {runTime:.5f} seconds!")


# Create a new round and store it in the database
def createRound(startTime):

    # Get (or create) planets in database
    planets = databasecontroller.get_planetObjectList()
    if len(planets) is 0:
        print(f"No planets exists, creating defaults")
        for planet in default_planets:
            databasecontroller.add_planetsToDB(planet)
        planets = databasecontroller.get_planetObjectList()
    if len(planets) < 4:
        print(f"WARNING: Only {len(planets)} planets exists in database - this situation is NOT handled!")

    tracks = []
    trackIds = []

    # Generate Track from each planet
    print("Generating Tracks...")
    existingTracks = databasecontroller.get_tracksObjectsList()
    for planet in planets:
        existingPlanetTracks = []
        for track in existingTracks:
            if track.planetId is planet._id:
                existingPlanetTracks.append(track)
        generatedTrack = generate.generateTrack(planet, existingPlanetTracks)
        print(f"\t{planet.name}: {generatedTrack}")
        tracks.append(generatedTrack)

    # Save tracks in database
    print("Storing Tracks in database")
    for track in tracks:
        databasecontroller.add_TrackObject(track)
        trackIds.append(track._id)

    # Figure out the next round number
    roundNumber = len(databasecontroller.get_roundsObjectList()) + 1

    print("Storing Round in database")
    round =  Round(roundNumber, trackIds,  startTime, startTime + _ROUND_LENGTH*60000)
    databasecontroller.add_roundObject(round)

    return round



# Updates the ranking of all the unranked rounds, and updates
# the Universal Ranking
def updateRankings(rankedRounds, unrankedRounds, allTracks):
    print("\nRanking rounds..")
    for unrankedRound in unrankedRounds:
        print(f"\tRound {unrankedRound.roundNumber}.. ", end="")
        roundTracks = []

        for track in allTracks:
            if track._id in unrankedRound.trackIds:
                roundTracks.append(track)

        ranker.rankRound(unrankedRound, roundTracks)
        rankedRounds.append(unrankedRound)

        print(f"Ranked {len(unrankedRound.rankings)} users.. ", end="")
        databasecontroller.update_round(unrankedRound)
        print("Updated in database")


    # Sort the ranked rounds (last end date to first end date)
    sortedRankedRounds = sorted(rankedRounds, key=lambda r: r.endDate, reverse=True)
    currentUniversalRounds = sortedRankedRounds[0:_UNIVERSAL_RANKING_ROUND_COUNT]

    # Get users and convert to dictionary (for faster acces)
    print("\nRanking Universe")
    userList = databasecontroller.get_UserObjectList()

    universalRankings = ranker.rankUniversal(currentUniversalRounds, userList)

    # Converts userlist to dictionary for ease of access
    users = {}
    for user in userList:
        users[user._id] = user

    rank = 0
    for userId, rating in universalRankings:
        rank += 1
        users[userId].rank = rank
        users[userId].rating = rating

    print("Storing in rankings in database:")
    for user in users.values():
        # TODO: Make user updating atomic!
        print(f"\t{user.username}: #{user.rank} ({user.rating})... ", end="")
        databasecontroller.updateUser(user)
        print("Updated!")

    print("Universe ranked!")



# Start the updater
def startUpdater():
    print("\nSTARTING UNIVERSE UPDATER!")

    # TODO: Remove test flag, when ready
    databasecontroller.initializeDatabase(testMode=_TEST_MODE, clearDatabase=_CLEAR_DATABSE_ON_START)

    while True:
        updateUniverse()
        print(f"\nUpdating again in {_UPDATE_FREQUENCY} seconds")
        time.sleep(_UPDATE_FREQUENCY)


# Start the updater if this script is run as a program
if __name__ == '__main__':
    startUpdater()
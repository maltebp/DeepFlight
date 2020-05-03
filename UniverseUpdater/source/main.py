
import time
import timeit # Used to measure run time
from source import ranker
from source import generate
from source.model.round import Round
from source.Database import DatabaseController
from source.model.planet import *

_UPDATE_FREQUENCY = 25 # seconds
_ROUND_LENGTH = 10 # Minutes

# Number of rounds to base the universal ranking upon
_UNIVERSAL_RANKING_ROUND_COUNT  = 4


def updateUniverse():

    print("\nUpdating Universe...")
    startTime = timeit.default_timer()

    rounds = DatabaseController.get_roundsObjectList()

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
        allTracks = DatabaseController.get_tracksObjectsList()
        updateRankings(rankedRounds, unrankedRounds, allTracks)
        print("Finished updating rankings")

    endTime = timeit.default_timer()
    runTime = endTime - startTime
    print(f"Finished update in {runTime:.5f} seconds!")


# Create a new round and store it in the database
def createRound(startTime):

    # Get (or create) planets in database
    planets = DatabaseController.get_planetObjectList()
    if len(planets) is 0:
        print(f"No planets exists, creating defaults")
        for planet in default_planets:
            DatabaseController.add_planetsToDB(planet)
        planets = DatabaseController.get_planetObjectList()
    if len(planets) < 4:
        print(f"WARNING: Only {len(planets)} planets exists in database - this situation is NOT handled!")

    tracks = []
    trackIds = []

    # Generate Track from each planet
    print("Generating Tracks...")
    existingTracks = DatabaseController.get_tracksObjectsList()
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
        trackId = DatabaseController.add_TrackObject(track)
        trackIds.append(trackId)

    # Figure out the next round number
    roundNumber = len(DatabaseController.get_roundsObjectList()) + 1

    print("Storing Round in database")
    round =  Round(roundNumber, trackIds,  startTime, startTime + _ROUND_LENGTH*60000)
    DatabaseController.add_roundObject(round)

    return round



# Updates the ranking of all the unranked rounds, and updates
# the Universal Ranking
def updateRankings(rankedRounds, unrankedRounds, allTracks):
    print("Ranking rounds..")
    for unrankedRound in unrankedRounds:
        print(f"\tRound {unrankedRound.roundNumber}... ", end="")
        roundTracks = []

        for track in allTracks:
            if track._id in unrankedRound.trackIds:
                roundTracks.append(track)

        ranker.rankRound(unrankedRound, roundTracks)
        rankedRounds.append(unrankedRound)

        print(f"Ranked {len(unrankedRound.rankings)} users")

        # TODO: Update round ranking in database

    # Sort the ranked rounds (last end date to first end date)
    sortedRankedRounds = sorted(rankedRounds, key=lambda r: r.endDate, reverse=True)
    currentUniversalRounds = sortedRankedRounds[0:_UNIVERSAL_RANKING_ROUND_COUNT]

    # Perform the ranking
    # TODO: Get users
    users = []
    universalRanking = ranker.rankUniversal(currentUniversalRounds, users)
    # TODO: Update user with new ranking in database
    print("Updated Universal Ranking")



# Start the updater
print("\nSTARTING UNIVERSE UPDATER!")
updateUniverse()
while True:
    time.sleep(_UPDATE_FREQUENCY)
    updateUniverse()
    print(f"\nUpdating again in {_UPDATE_FREQUENCY} seconds")
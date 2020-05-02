
import source.DatabaseController
import time
from source.ranker import *
from source.generate import *
from source.model.round import Round

_UPDATE_FREQUENCY = 60 # seconds
_ROUND_LENGTH = 1440 # Minutes


# Number of rounds to base the universal ranking upon
_UNIVERSAL_RANKING_ROUND_COUNT  = 4


def updateUniverse():

    print("\nUpdating Universe...")

    # TODO: Get rounds from database
    # rounds = get_rounds()
    rounds = []

    currentRound = None
    unrankedRounds = []
    futureRounds = []
    rankedRounds = []

    currentTime = int(round(time.time() * 1000))

    # Check status of each round
    for round in rounds:
        if round.startDate < currentTime < round.endDate:
            if currentRound is not None:
                print("WARNING: More than 1 current round!")
            currentRound = round

        if round.endDate < currentTime:
            if round.rankings is not None:
                rankedRounds.append(round)
            else:
                unrankedRounds.append(round)

        if round.startDate > currentTime:
            futureRounds.append(round)



    print(f"Total Rounds:     {len(rounds)}")
    print(f"Current Rounds:  {currentRound}")
    print(f"Unranked Rounds:  {len(unrankedRounds)}")
    print(f"Future Rounds:    {len(futureRounds)}")

    if currentRound is None:
        currentRound = createRound(currentTime)

    if len(futureRounds) is 0:
        createRound(currentRound.endTime)

    if len(unrankedRounds) > 0:
        updateRankings(rankedRounds, unrankedRounds)


    print("Finished update!")


# Create a new round and store it in the database
def createRound(startTime):

    # TODO: Get planets from database
    planets = []
    tracks = []
    trackIds = []

    for planet in planets:
        # TODO: Get planet tracks
        existingTracks = []
        tracks.append( generateTrack(planet, existingTracks) )
        # TODO: Store track in database
        # TODO: Consider how ID is added to track after insert in database

    round =  Round(0, trackIds, startTime, startTime + _ROUND_LENGTH*60000)
    # TODO: Store round in database
    return round



# Updates the ranking of all the unranked rounds, and updates
# the Universal Ranking
def updateRankings(rankedRounds, unrankedRounds):
    for unrankedRound in unrankedRounds:
        print(f"Ranking Round {unrankedRound.roundNumber}")
        roundTracks = []

        for trackId in round.trackId:
            # TODO: Get track from database
            # roundTracks.append(get_track(trackId))
            pass

        rankRound(unrankedRound, roundTracks)
        rankedRounds.append(unrankedRounds)

        # TODO: Update round ranking in database

    # Sort the ranked rounds (last end date to first end date)
    sortedRankedRounds = sorted(rankedRounds, key=round.endDate, reverse=True)

    # Perform the ranking
    # TODO: Get users
    universalRanking = rankUniversal(sortedRankedRounds, rankedRounds)
    # TODO: Update user with new ranking in database
    print("Updated Universal Ranking")





print("\nSTARTING UNIVERSE UPDATER!\n")
updateUniverse()
while True:
    time.sleep(_UPDATE_FREQUENCY)
    updateUniverse()
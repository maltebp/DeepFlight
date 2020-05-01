
from source.model.track import Track
from source.model.round import Round
from source.model.user import User

#
# EXPLANATION OF RANKING ALGORITHM
#
# Summary:
#
#   for track in round.tracks:
#       rate track on times
#   rate round on tracks
#
#   rate universal on previous N rounds
#
# Track Rating:
#   Each user who has a time on the track (and thus played and finished the Track),
#   is ranked with best user being rank 1.
#   Each users' rating in then calculated by:
#
#       user rating = 1 - (rank-1)/(totalranks)
#
# Round Rating
#   Track Rating is calculated for each Track within the round
#   Each users Round Rating, is calculated by averaging combining Track Ratings
#
# Universal Rating
#   Each user's Universal Rating is calculated by averaging the 4 user's Round
#   Rating for N previous Rounds (N being a variable determined outside this)#


#
# Ranks the given round, based on the given tracks
def rankRound(round, tracks):
    if round.rankings is not None:
        raise ValueError(f"Round {round.roundNumber} has already been ranked")

    ratings = {}
    for track in tracks:
        trackRatings = _rankTrack(track)
        for user, rating in trackRatings.items():
            if user in ratings:
                ratings[user] += rating
            else:
                ratings[user] = rating

    round.rankings = ratings

    print(ratings)

    return round


# Ranks a specific track
def _rankTrack(track):
    times = track.times
    numTimes = len(times)

    # Sorts the times
    sortedTimes = sorted(times.items(), key=lambda x: x[1])

    # Calculate track rating for each user
    trackRatings = {}
    trackRank = 1
    for user, time in sortedTimes:
        # Round rating to 3 decimal places
        trackRatings[user] = (1 - (trackRank-1)/(numTimes)) * 2.5
        trackRank += 1

    return trackRatings


#
# Calculate the Universal Rating for each of the given users
# based upon the given rounds
def rankUniversal(rounds, users):
    # number of rounds user has participating in
    userRoundCount = {}
    userRating = {}

    # Add all users with a rating of 0
    for user in users:
        userRating[user.username] = 0
        userRoundCount[user.username] = 0

    # Sum the ratings of the given rounds for each user
    for round in rounds:
        if round.rankings is None:
            raise ValueError(f"Round {round.Number} has not been ranked yet")
        for username, rating in round.rankings.items():
            if username in userRating: # Just an extra sanity check
                userRating[username] += rating
                userRoundCount[username] += 1

    # Average the ratings for each user
    for username, rating in userRating.items():
        roundCount = userRoundCount[username]

        # This reduces the penalty for not playing a round at all
        # I.e. if a user has only played 2 out of 4 rounds, his
        # total rating is divided by 2+(4-2)*0.5=3 instead of 4
        adjustedRoundCount = roundCount + (len(rounds)-roundCount)*0.5
        userRating[username] = rating / adjustedRoundCount

    return userRating





# --------------------------------------------------------------------------------------------------
# Just some test data

#
# testTracks = [
#     Track("test track", 1, times={ "Erlend" : 10, "Malte" : 4, "Andreas" : 1, "Rasmus" : 2} ),
#     Track("test track", 1, times={ "Erlend" : 2, "Andreas" : 4, "Rasmus" : 1} ),
#     Track("test track", 1, times={ "Erlend" : 2, "Malte" : 2, "Rasmus" : 1} ),
#     Track("test track", 1, times={ "Erlend" : 2, "Malte" : 2, "Rasmus" : 1} )
# ]
#
# rounds= [
#     Round(1, None, 1, 0, 0),
#     Round(2, None, 1, 0, 0),
#     Round(3, None, 1, 0, 0),
#     Round(4, None, 1, 0, 0)
# ]
# rankRound(rounds[0], testTracks)
# rankRound(rounds[1], testTracks)
# rankRound(rounds[2], testTracks)
# rankRound(rounds[3], testTracks)
#
# users = [
#      User(0, "Erlend"),
#      User(0, "Malte"),
#      User(0, "Rasmus"),
#      User(0, "Andreas")
# ]
#
#
# universalRankings = rankUniversal(rounds,users)
# print(universalRankings)

class Round:

    #
    # start-, endDate: Date time in millesconds
    # rankings: Dictionary of usernames paired with ratings
    def __init__(self, id,trackIds, roundNumber, startDate, endDate , rankings=None):
        self.id = id
        self.trackIds = trackIds
        self.roundNumber = roundNumber
        self.startDate = startDate
        self.endDate = endDate
        self.rankings = rankings


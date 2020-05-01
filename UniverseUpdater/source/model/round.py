from source.model.ranking import *
class Round:

    #
    # start-, endDate: Date time in millesconds
    # rankings: Dictionary of usernames paired with ratings
    def __init__(self,trackIds, roundNumber, startDate, endDate , rankings):
        self._id = None
        self.trackIds = trackIds
        self.roundNumber = roundNumber
        self.startDate = startDate
        self.endDate = endDate
        self.rankings = rankings

    def setId(self, id):
        self._id = id




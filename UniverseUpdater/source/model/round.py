from source.model.ranking import *
import json
class Round:

    #
    # start-, endDate: Date time in millesconds
    # rankings: Dictionary of usernames paired with ratings

    def __init__(self,trackIds, roundNumber, startDate, endDate , rankings=None):
        self._id = None
        self.trackIds = trackIds
        self.roundNumber = roundNumber
        self.startDate = startDate
        self.endDate = endDate
        self.rankings = rankings

    def setId(self,id):
        self._id=id

    def __str__(self):
        rankings =[]
        for rank in self.rankings:
            rankings.append(rank.__str__())
        return f"Track( id={self._id}, trackId='{self.trackIds}', roundNumber={self.roundNumber}, startDate={self.startDate}, endDate={self.endDate},{rankings} )"

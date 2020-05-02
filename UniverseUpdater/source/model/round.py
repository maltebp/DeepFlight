
import datetime

class Round:

    #
    # start-, endDate: Date time in millesconds
    # rankings: Dictionary of usernames paired with ratings

    def __init__(self, roundNumber,  trackIds, startDate, endDate , rankings=None):
        self._id = None
        self.roundNumber = roundNumber
        self.trackIds = trackIds
        self.startDate = startDate
        self.endDate = endDate
        self.rankings = rankings

    def setId(self,id):
        self._id=id

    def __str__(self):
        rankingsString = "None" if self.rankings is None else len(self.rankings)
        return f"Round( id={self._id}, roundNumber={self.roundNumber}, trackIds={self.trackIds}, startDate='{_date_to_string(self.startDate)}', endDate='{_date_to_string(self.endDate)}', rankings={rankingsString} )"


def _date_to_string(time):
    date = datetime.datetime.fromtimestamp(time / 1000.0)
    return f"{date.day:02d}/{date.month:02d} {date.hour:02d}:{date.minute:02d}:{date.second:02d}"

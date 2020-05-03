
class User:

    def __init__(self, username, rank=None, rating=None, id=None):
        self._id = id
        self.username = username
        self.rank = rank
        self.rating = rating


    def setId(self, id):
        self._id=id


    def __str__(self):
        return f"User( id={str(self._id)}, username='{self.username}', rank={self.rank}, rating={self.rating})"


class User:

    def __init__(self,username, rank=None, rating=None):
        self._id = None
        self.username = username
        self.rank = rank
        self.rating = rating


    def setId(self,id):
        self._id=id


    def __str__(self):
        return f"Track( id={str(self._id)}, username='{self.username}', rank={self.rank}, rating={self.rating})"

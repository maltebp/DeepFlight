
class User:

    def __init__(self, id, username, rank=None, rating=None):
        self.id = id
        self.username = username
        self.rank = rank
        self.rating = rating
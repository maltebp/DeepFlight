class Ranking:

        # start-, endDate: Date time in millesconds
        # rankings: Dictionary of usernames paired with ratings
        def __init__(self,user_id, rating, rank):
            self.rank = rank
            self.rating = rating
            self.user_id = user_id


        def __str__(self):
            return f"rankings( user_id={self.user_id}, rating='{self.rating}', rank={self.rank})"


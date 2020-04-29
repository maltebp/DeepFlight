##The track object/DTO
class Track:

    def __init__(self, id, name, planet_id):
        self.id = id
        self.name = name
        self.planetId = planet_id
        self.data = None


    def __str__(self):
        return f"Track( id={self.id}, name='{self.name}', planetId={self.planetId} )"

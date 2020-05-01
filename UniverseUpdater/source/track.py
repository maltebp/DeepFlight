##The track object/DTO
class Track:

    def __init__(self, id, length, name, planet_id,):
        self.id = id
        self.length = length
        self.name = name
        self.planetId = planet_id



    def __str__(self):
        return f"Track( id={self.id},length='{self.length}', name='{self.name}', planetId={self.planetId} )"



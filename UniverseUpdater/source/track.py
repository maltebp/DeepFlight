##The track object/DTO
class Track:

    def __init__(self, id, name, planet_id, data=None, seed=None):
        self.id = id
        self.name = name
        self.planetId = planet_id
        self.data = data
        self.seed = seed


    def __str__(self):
        dataString = "None" if self.data is None else (str(len(self.data)) + " bytes")
        seedString = "None" if self.seed is None else str(self.seed)
        return f"Track( id={self.id}, name='{self.name}', planetId={self.planetId}, seed={seedString}, data={dataString} )"


    def setData(self,data):
        self.data = data

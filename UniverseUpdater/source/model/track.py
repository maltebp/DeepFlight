##The track object/DTO
class Track:


    #
    # times: Dictionary of username and their track times in milliseconds
    def __init__(self, id, name, planetId, seed=None, times=None, data=None, ):
        self._id = id
        self.name = name
        self.planetId =  planetId
        self.data = data
        self.seed = seed
        self.times = times


    def __str__(self):
        dataString = "None" if self.data is None else (str(len(self.data)) + " bytes")
        seedString = "None" if self.seed is None else str(self.seed)
        return f"Track( id={self._id}, name='{self.name}', planetId={self.planetId}, seed={seedString}, data={dataString} )"


    def setData(self,data):
        self.data = data


class Planet:

    def __init__(self,name, color, lengthFactor, curveFactor, stretchFactor, widthFactor, widthNoiseFactor):
        self._id = None
        self.name = name
        self.color = color
        self.lengthFactor = lengthFactor
        self.curveFactor = curveFactor
        self.stretchFactor = stretchFactor
        self.widthFactor = widthFactor
        self.widthNoiseFactor = widthNoiseFactor

    def setId(self,id):
        self._id=id

    def __str__(self):
        return f"Track( id={self._id}, name='{self.name}', color={self.color}, lengthFactor={self.lengthFactor}, curveFactor={self.curveFactor}, stretchFactor={self.stretchFactor}, widthFactor={self.widthFactor}, widthNoiseFactor={self.widthNoiseFactor} )"
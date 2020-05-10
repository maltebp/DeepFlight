
class Planet:

    def __init__(self,name, color, lengthFactor,  stretchFactor, curveFactor, widthFactor, widthNoiseFactor):
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




default_planets = [
					    #l  #s  #c  #w  #wn
    Planet("Smar",      [180, 80, 70],      11,  9, 16, 12, 25), # 11
    Planet("Aerth",     [0, 102, 0],        10, 8, 14, 12, 10), # 10
    Planet("Turnsa",    [100, 40, 170],     14, 16, 14, 18,  5), # 13
    Planet("Lupto",     [100, 147, 219],    15, 12, 13, 12,  1) # 13
]

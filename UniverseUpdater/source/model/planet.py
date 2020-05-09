
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




default_planets = [
					    #l  #s  #c  #w  #wn
    Planet("Smar",      [230, 90, 65],      11,  9, 18, 11, 18), # 11
    Planet("Aerth",     [0, 102, 0],        10, 11, 10, 12, 10), # 10
    Planet("Turnsa",    [127, 63, 192],     13, 16, 10, 13,  8), # 13
    Planet("Lupto",     [100, 147, 219],    13, 12, 14, 12,  1) # 13
]

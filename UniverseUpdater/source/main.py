

from source import database

#Hej Andreas


print("Starting Universe Update")

planets = database.get_planets()

print(*planets, sep="\n")


tracks = database.get_tracks()
print(*tracks, sep="\n")
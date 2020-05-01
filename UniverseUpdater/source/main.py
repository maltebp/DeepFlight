

from source import databaseDAO
from source.model.track import Track

print("Starting Universe Update")

planets = databaseDAO.get_planets()

databaseDAO.get_track_data()

print(*planets, sep="\n")

track = Track(None, "My Planet", 1)
track.data = "Hello World!".encode()
print(track.data)
databaseDAO.add_track(track)


tracks = databaseDAO.get_tracks()
print(*tracks, sep="\n")


from source import database
from source.model.track import Track

print("Starting Universe Update")

planets = database.get_planets()

database.get_track_data()

print(*planets, sep="\n")

track = Track(None, "My Planet", 1)
track.data = "Hello World!".encode()
print(track.data)
database.add_track(track)


tracks = database.get_tracks()
print(*tracks, sep="\n")
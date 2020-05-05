package database;

import com.mongodb.MongoQueryException;
import model.Planet;
import model.Round;
import model.Track;
import model.User;

import java.util.List;


public interface IDatabaseDAO {

    Planet getPlanet(String userId);

    Planet getPlanets();

    User getUser(String userId);

    List<User> getUsers();

    Track getTrack(String trackId);

    byte[] getTrackData(String trackId);

    Round updateTrackTime(String trackId, String userId, int time);

    Round getRounds();

    Round getCurrentRound();


}

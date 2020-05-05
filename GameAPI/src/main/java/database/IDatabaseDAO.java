package database;

import com.mongodb.MongoQueryException;
import model.Planet;
import model.Round;
import model.Track;
import model.User;

import java.util.List;
import java.util.NoSuchElementException;


public interface IDatabaseDAO {

    Planet getPlanet(String userId) throws DatabaseException, NoSuchElementException;

    List<Planet> getPlanets() throws DatabaseException;

    User getUser(String userId) throws DatabaseException, NoSuchElementException;

    List<User> getUsers() throws DatabaseException;

    Track getTrack(String trackId) throws DatabaseException, NoSuchElementException;

    byte[] getTrackData(String trackId) throws DatabaseException, NoSuchElementException;

    boolean updateTrackTime(String trackId, String userId, int time) throws DatabaseException, NoSuchElementException;

    List<Round> getRounds() throws DatabaseException;

    Round getCurrentRound() throws DatabaseException;

}

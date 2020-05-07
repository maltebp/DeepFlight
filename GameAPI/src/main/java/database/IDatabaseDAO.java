package database;

import model.Planet;
import model.Round;
import model.Track;
import model.User;


import java.util.List;
import java.util.NoSuchElementException;


public interface IDatabaseDAO {

    Planet getPlanet(String planetId) throws DatabaseException, NoSuchElementException;

    List<Planet> getPlanets() throws DatabaseException;

    User getUser(String userId) throws DatabaseException, NoSuchElementException;

    User getUserFromUsername(String username) throws DatabaseException, NoSuchElementException;


    /**
     * Create a new user in the database with the given username. If a user
     * with that name already exists, then nothing happens.
     *
     * @param username Username of the user to create
     * @return The User created (including id and username), or the User which already exists
     * @throws DatabaseException
     */
    User addUser(String username) throws DatabaseException;

    List<User> getUsers() throws DatabaseException;

    void deleteUser(String userId) throws DatabaseException;

    Track getTrack(String trackId) throws DatabaseException, NoSuchElementException;

    byte[] getTrackData(String trackId) throws DatabaseException, NoSuchElementException;

    boolean updateTrackTime(String trackId, String userId, int time) throws DatabaseException, NoSuchElementException;

    List<Round> getRounds() throws DatabaseException;

    Round getCurrentRound() throws DatabaseException, NoSuchElementException;

    Round getPreviousRound() throws DatabaseException, NoSuchElementException;

}

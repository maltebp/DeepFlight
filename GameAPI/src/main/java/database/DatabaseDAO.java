package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.query.*;
import model.*;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.NoSuchElementException;

public class DatabaseDAO implements IDatabaseDAO {

    private Datastore database;

    public DatabaseDAO(){
      database = DatabaseConnection.getInstance().getDatastore();
    }

    public List<Track> getAllTracks(){
        return database.createQuery(Track.class).find().toList();
    }

    @Override
    public Planet getPlanet(String planetId) throws DatabaseException, NoSuchElementException {
        try{
            Planet planet = database.createQuery(Planet.class).field("id").equal(new ObjectId(planetId)).first();
            if (planet == null) {
                throw new NoSuchElementException(String.format("No such Planet exist. PlanetID: %s", planetId));
            }
            return planet;
        }catch(IllegalArgumentException e){
            throw new NoSuchElementException("Couldn't find Track with that ID: " + e.getMessage());
        }
    }

    @Override
    public List<Planet> getPlanets() throws DatabaseException {
        return database.createQuery(Planet.class).find().toList();
    }

    @Override
    public User getUser(String userId) throws DatabaseException, NoSuchElementException {
        User user = database.createQuery(User.class).field("id").equal(new ObjectId(userId)).first();
        if (user == null) {
            throw new NoSuchElementException(String.format("No such User exist. UserID: %s", userId));
        }
        return user;
    }

    @Override
    public User getUserFromUsername(String username) throws DatabaseException, NoSuchElementException {
        User user = database.createQuery(User.class).field("username").equal(username).first();
        if (user == null) {
            throw new NoSuchElementException(String.format("No such User exist. UserName: %s", username));
        }
        return user;
    }

    @Override
    public User addUser(String username) throws DatabaseException {
        User newUser = new User(username);
        String userIdString = database.save(newUser).getId().toString();
        newUser.setId(userIdString);

        return newUser;
    }

    @Override
    public List<User> getUsers() throws DatabaseException {
        return database.createQuery(User.class).find().toList();
    }

    @Override
    public void deleteUser(String userId) throws DatabaseException {
        Query<User> deleteQuery = database.createQuery(User.class).field("id").equal(new ObjectId(userId));
        database.delete(deleteQuery);
    }

    @Override
    public Track getTrack(String trackId) throws DatabaseException, NoSuchElementException {
        try{
            Track track = database.createQuery(Track.class).field("id").equal(new ObjectId(trackId)).first();
            if (track == null) {
                throw new NoSuchElementException(String.format("No such Track exist. trackID: %s", trackId));
            }
            return track;
        }catch(IllegalArgumentException e){
            throw new NoSuchElementException();
        }

    }

    @Override
    public Trackdata getTrackData(String trackId) throws DatabaseException, NoSuchElementException {
        Trackdata trackdata = database.createQuery(Trackdata.class).field("id").equal(new ObjectId(trackId)).first();
        if (trackdata == null) {
            throw new NoSuchElementException(String.format("No such Track exist. trackID: %s", trackId));
        }
        return trackdata;
    }

    @Override
    public boolean updateTrackTime(String trackId, String userId, int time) throws DatabaseException {
        Query<Track> trackQuery = database.createQuery(Track.class).field("id").equal(new ObjectId(trackId));

        UpdateOperations<Track> updateOperations = database.createUpdateOperations(Track.class)
                .set(String.format("times.%s", userId),time);

        UpdateResults updateResults = database.update(trackQuery,updateOperations);

        return updateResults.getWriteResult().wasAcknowledged();
    }

    @Override
    public List<Round> getRounds() throws DatabaseException {
        return database.createQuery(Round.class).find().toList();
    }

    @Override
    public Round getCurrentRound() throws DatabaseException,NoSuchElementException {
        long currEpochMillis = System.currentTimeMillis();
        Round round = getRoundFromEpochMillis(currEpochMillis);
        if (round == null) {
            throw new NoSuchElementException(String.format("No such Round exist. currentEpochMillis: %d", currEpochMillis));
        }
        return round;
    }

    @Override
    public Round getPreviousRound() throws DatabaseException, NoSuchElementException {
        Round currentRound = getCurrentRound();
        Round previousRound = getRoundFromRoundNumber(currentRound.getRoundNumber()-1);
        if (previousRound == null) {
            throw new NoSuchElementException("No such Round exist. ");
        }

        return previousRound;
    }

    private Round getRoundFromEpochMillis(long epochMillis) {
        Query<Round> q = database.createQuery(Round.class);
        q.and(q.criteria("startDate").lessThanOrEq(epochMillis),
                q.criteria("endDate").greaterThanOrEq(epochMillis));

        return q.first();
    }

    private Round getRoundFromRoundNumber(long roundNumber) throws DatabaseException {
        return database.createQuery(Round.class).field("roundNumber").equal(roundNumber).first();
    }


    /** String enum identifying a Collection within the Mongo database */
  public enum Collection {
        PLANETS("planets"),
        TRACKS("Track"),
        ROUNDS("rounds"),
        TRACKDATA("trackdata");

        private final String name;

        Collection(String name){ this.name = name; }

        @Override
        public String toString() { return name; }
    }


    private static MongoClient createClient(){
        return MongoClients.create("mongodb+srv://game:deepflightisawesome@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority");
    }




    // -----------------------------------------------------------------------------------------------
    // TEST MODE


    /**
     * Enables test mode for future database.DatabaseDAO objects, such that they will
     * use a temporary test database.
     * The test database name is DB_NAME+_test, and it's recreated when this method
     * is called. */
  /* public static void enableTestMode() throws UnknownHostException {
        testMode = true;
        /*MongoClient client = createClient();
        client.getDatabase(DB_NAME + "_test").drop();
        client.close();
        setupTestDatabase();
    }

    /** Adds test data to test database */
   /* private static void setupTestDatabase() throws UnknownHostException {
        DatabaseDAO db = new DatabaseDAO();
        db.addPlanet(new Planet(1, "Smar", new int[]{123,150,111}));
        db.addPlanet(new Planet(2, "Turnsa", new int[]{200,100,50}));
        db.addPlanet(new Planet(3, "Lupto", new int[]{150,50,100}));
        db.addPlanet(new Planet(4, "Aerth", new int[]{255,150,125}));

        db.addTrack(new Track(1, "ABCD123", 1, 1000));
        db.addTrack(new Track(2, "ASDJ685", 2, 3000));
        db.addTrack(new Track(3, "PIDF564", 3, 2000));
        db.addTrack(new Track(4, "OKGJ884", 4, 1500));

        db.addTrackBlockData( 1, TrackDataReader.getTrackData("smar.dftbd") );
        db.addTrackBlockData( 2, TrackDataReader.getTrackData("turnsa.dftbd") );
        db.addTrackBlockData( 3, TrackDataReader.getTrackData("lupto.dftbd") );
        db.addTrackBlockData( 4, TrackDataReader.getTrackData("aerth.dftbd") );

        db.addRound(new Round(1, new int[]{1,2,3,4}, System.currentTimeMillis(), System.currentTimeMillis() + 86400000));

        db.close();
    }*/

}

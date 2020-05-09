package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

import model.Planet;
import model.Round;
import model.Track;

import java.net.UnknownHostException;
import java.util.HashSet;

public class DatabaseConnection {
    private final String DB_NAME = "gamedb";
    private final String DB_TESTING_SUFFIX = "_test";
    private final String DB_USER = "universeupdater";
    private final String DB_PASSWORD = "deepflightisawesome";
    private final String DB_SERVER_URL = "mongodb+srv://"+DB_USER+":"+DB_PASSWORD+"@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority";
    private static String dbName = "";

    private static boolean testMode = true;

    private Morphia morphia;
    private MongoClient mongoClient;
    private static DatabaseConnection instance = new DatabaseConnection();
    private static Datastore datastore;

    private DatabaseConnection() {
        MongoClientURI uri = new MongoClientURI(DB_SERVER_URL);
        mongoClient = new MongoClient(uri);
        HashSet<Class> classes = new HashSet<>();
        classes.add(Track.class);
        classes.add(Planet.class);
        classes.add(Round.class);
        morphia = new Morphia(classes);
        dbName = createDbNameFromMode(testMode);
        datastore = createDatastoreOfDbName(dbName);
    }

    public static DatabaseConnection getInstance(){
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Datastore getDatastore () {
        return datastore;
    }

    /**
     * Enables test mode for future database.DatabaseDAO objects, such that they will
     * use a temporary test database.
     * The test database name is DB_NAME+_test, and it's recreated when this method
     * is called. */
    public void setTestMode(boolean isTesting) throws UnknownHostException {
        if (testMode != isTesting) {
            testMode = isTesting;
            dbName = createDbNameFromMode(testMode);
            datastore = createDatastoreOfDbName(dbName);
        }
    }

    /**
     * Returns the correct name for the DB, matching the inputted isTesting boolean.
     * @param isTesting the boolean representation of the testing mode.
     * @return DB name depending on inputted state.
     */
    private String createDbNameFromMode(boolean isTesting) {
        return DB_NAME + (isTesting ? DB_TESTING_SUFFIX : "");
    }

    /**
     * Returns a Datastore object matching the inputted DB name.
     * @param dbName is name on the DB to create correlating Datastore of.
     * @return Datastore matching the inputted DB name.
     */
    private Datastore createDatastoreOfDbName (String dbName){
        return morphia.createDatastore(mongoClient, dbName);
    }
}
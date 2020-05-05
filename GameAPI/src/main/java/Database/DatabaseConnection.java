package Database;

import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import model.tracks;

import java.net.UnknownHostException;
import java.util.HashSet;

public class DatabaseConnection {
    private static final String DB_NAME = "gamedb";
    private static final String DB_USER = "universeupdater";
    private static final String DB_PASSWORD = "deepflightisawesome";
    private static final String DB_SERVER_URL = "mongodb+srv://"+DB_USER+":"+DB_PASSWORD+"@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority";
    private static String dbName = "";

    private static boolean testMode = true;


    //private MongoDatabase database;
    private static MongoClient client;
    private static DatabaseConnection instance = new DatabaseConnection();
    private static Datastore handler;



    private DatabaseConnection() {
        MongoClientURI uri = new MongoClientURI(DB_SERVER_URL);
        com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient(uri);
        HashSet<Class> classes = new HashSet<>();
        classes.add(tracks.class);
        Morphia m = new Morphia(classes);
        dbName = DB_NAME + (testMode ? "_test" : "");
        handler = m.createDatastore(mongoClient, dbName);
        //client = createClient();
        // database = client.getDatabase(dbName);
    }

    public static Datastore getInstance(){
        return handler;
    }





    /**
     * Enables test mode for future Database.DatabaseDAO objects, such that they will
     * use a temporary test database.
     * The test database name is DB_NAME+_test, and it's recreated when this method
     * is called. */
    public static void enableTestMode() throws UnknownHostException {
        testMode = true;
        /*MongoClient client = createClient();
        client.getDatabase(DB_NAME + "_test").drop();
        client.close();
        setupTestDatabase();*/
    }
}

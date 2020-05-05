package Database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;

public class DatabaseConnection {
    private static final String DB_NAME = "gamedb";
    private static final String DB_USER = "universeupdater";
    private static final String DB_PASSWORD = "deepflightisawesome";
    private static final String DB_SERVER_URL = "mongodb+srv://"+DB_USER+":"+DB_PASSWORD+"@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority";
    private static String dbName = "";

    private static boolean testMode = true;

    private MongoClient client;
    private MongoDatabase database;


    private static DatabaseConnection ourInstance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return ourInstance;
    }

    private DatabaseConnection() {

        dbName = DB_NAME + (testMode ? "_test" : "");

        MongoClient mongoClient = MongoClients.create(DB_SERVER_URL);
        database = mongoClient.getDatabase(dbName);

        //client = createClient();
        // database = client.getDatabase(dbName);
    }


    public MongoDatabase getDatabase(){
        return database;
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

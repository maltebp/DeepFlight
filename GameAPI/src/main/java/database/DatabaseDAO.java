package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import model.Track;

import java.util.List;

public class DatabaseDAO {

    private Datastore database;

    // TODO: Fix password in code


    public DatabaseDAO(){
      database = DatabaseConnection.getInstance();

       // database.getDatabase().getCollection(Collection.PLANETS.toString());

    }


public List<Track> getAllTracks(){

    Query<Track> query = DatabaseConnection.getInstance().find(Track.class);
    return query.asList();
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

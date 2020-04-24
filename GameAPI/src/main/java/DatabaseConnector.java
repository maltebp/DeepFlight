
import com.mongodb.*;
import model.Planet;
import model.Round;
import model.Track;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class DatabaseConnector {

    private static final String DB_SERVER_URL = "mongodb://localhost:27017";
    private static final String DB_NAME = "deepflight_gamedb";

    private static boolean testMode = false;

    private MongoClient client;
    private DB database;


    public DatabaseConnector() throws UnknownHostException {
        String dbName = DB_NAME + (testMode ? "_test" : "");
        client = new MongoClient("");
        database = client.getDB(dbName);
    }


    public void addPlanet(Planet planet){
        DBCollection collection = database.getCollection(Collection.PLANETS.toString());
        collection.insert(planet.toMongoObject());
    }


    public Planet getPlanet(int planetId) {
        DBCollection trackCollection = database.getCollection( Collection.PLANETS.toString() );

        DBObject responseObject = trackCollection.findOne(new BasicDBObject("_id", planetId));

        if( responseObject == null ) return null;

        return Planet.fromMongoObject(responseObject);
    }



    public List<Planet> getPlanets(){
        DBCollection collection = database.getCollection(Collection.PLANETS.toString());

        List<Planet> planets = new LinkedList<>();
        collection.find().forEach( (object) -> {
            planets.add(Planet.fromMongoObject(object));
        });

        return planets;
    }



    public void addTrack(Track track){
        DBCollection collection = database.getCollection( Collection.TRACKS.toString());
        collection.insert(track.toMongoObject());
    }



    public Track getTrack(int trackId) {
        DBCollection trackCollection = database.getCollection( Collection.TRACKS.toString() );

        DBObject responseObject = trackCollection.findOne(new BasicDBObject("_id", trackId));

        if( responseObject == null ) return null;

        return Track.fromMongoObject(responseObject);
    }




    public void addTrackBlockData(int trackId, byte[] trackData){

        DBCollection collection = database.getCollection(Collection.TRACKDATA.toString());

        BasicDBObject object = new BasicDBObject()
                .append("_id", trackId)

                // Stores the binary as base65 encoded (consider doing this manually)
                .append("data", trackData);//new Binary(trackData));


        collection.insert(object);
    }


    public byte[] getTrackData(int trackId){
        DBCollection collection = database.getCollection(Collection.TRACKDATA.toString());

        BasicDBObject query = new BasicDBObject()
                .append("_id", trackId);

        BasicDBObject result = (BasicDBObject) collection.findOne(query);

        if( result == null){
            System.out.println("Result is null");
            return null;
        }
        //byte[] bytes = result.getString("data").getBytes();


        // This retrieves the bytes correctly
        byte[] bytes = (byte[]) result.get("data");


        return bytes;

        //return bytes;
    }


    public void addRound(Round round){
        DBCollection collection = database.getCollection(Collection.ROUNDS.toString());
        collection.insert(round.toMongoObject());
    }



    public List<Round> getRounds(){
        DBCollection collection = database.getCollection(Collection.ROUNDS.toString());

        List<Round> rounds = new LinkedList<>();
        collection.find().forEach( (object) -> {
            rounds.add(Round.fromMongoObject(object));
        });

        return rounds;
    }



    public void close(){
        if( client != null ) {
            // Closes client and database
            client.close();
            client = null;
        }
    }


    /** String enum identifying a Collection within the Mongo database */
    public enum Collection {
        PLANETS("planets"),
        TRACKS("tracks"),
        ROUNDS("rounds"),
        TRACKDATA("trackdata");

        private final String name;

        Collection(String name){ this.name = name; }

        @Override
        public String toString() { return name; }
    }






    // -----------------------------------------------------------------------------------------------
    // TEST MODE


    /**
     * Enables test mode for future DatabaseConnector objects, such that they will
     * use a temporary test database.
     * The test database name is DB_NAME+_test, and it's recreated when this method
     * is called. */
    public static void enableTestMode() throws UnknownHostException {
        testMode = true;
        MongoClient client = new MongoClient(new MongoClientURI(DB_SERVER_URL));
        client.dropDatabase(DB_NAME + "_test");
        client.close();
        setupTestDatabase();
    }

    /** Adds test data to test database */
    private static void setupTestDatabase() throws UnknownHostException {
        DatabaseConnector db = new DatabaseConnector();

        db.addPlanet(new Planet());
        db.addPlanet(new Planet(1, "Smar", new int[]{123,150,111}));
        db.addPlanet(new Planet(2, "Turnsa", new int[]{200,100,50}));
        db.addPlanet(new Planet(3, "Lupto", new int[]{150,50,100}));
        db.addPlanet(new Planet(4, "Aerth", new int[]{255,150,125}));

        db.addTrack(new Track(1, "ABCD123", 1, 1000));
        db.addTrack(new Track(2, "ASDJ685", 2, 3000));
        db.addTrack(new Track(3, "PIDF564", 3, 2000));
        db.addTrack(new Track(4, "OKGJ884", 4, 1500));

        db.addTrackBlockData( 1, TrackDataReader.getTrackData("smar.dft") );
        db.addTrackBlockData( 2, TrackDataReader.getTrackData("turnsa.dft") );
        db.addTrackBlockData( 3, TrackDataReader.getTrackData("lupto.dft") );
        db.addTrackBlockData( 4, TrackDataReader.getTrackData("aerth.dft") );

        db.addRound(new Round(1, new int[]{1,2,3,4}, System.currentTimeMillis(), System.currentTimeMillis() + 86400000));

        db.close();
    }

}

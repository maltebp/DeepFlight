
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.application.Platform;
import model.Planet;
import model.Round;
import model.Track;
import org.bson.Document;
import org.bson.types.Binary;
import org.jongo.Jongo;
import org.mongojack.JacksonMongoCollection;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

public class DatabaseConnector {

    private static final String DB_SERVER_URL = "mongodb://localhost:27017";

    // TODO: Fix password in code
    private static final String DB_NAME = "gamedb";
    private static final String DB_USER = "game";
    private static final String DB_PASSWORD = "deepflightisawesome";

    private static boolean testMode = false;

    private MongoClient client;
    private MongoDatabase database;

    public DatabaseConnector() throws UnknownHostException {
        String dbName = DB_NAME + (testMode ? "_test" : "");

        client = createClient();
        database = client.getDatabase(dbName);
    }


    public void addPlanet(Planet planet){
        MongoCollection<Document> collection = database.getCollection(Collection.PLANETS.toString());
        collection.insertOne(planet.toDocument());
    }


    public Planet getPlanet(int planetId) {
        MongoCollection<Document> trackCollection = database.getCollection( Collection.PLANETS.toString() );

        Document responseObject = trackCollection.find(Filters.eq("_id", planetId)).first();

        if( responseObject == null ) return null;

        return Planet.fromDocument(responseObject);

    }

    public Planet getPlanetMongoJack(int planetId) {
        JacksonMongoCollection<Planet> collection = JacksonMongoCollection.builder()
                .build(client, DB_NAME, Collection.PLANETS.toString(), Planet.class);

        return collection.find(Filters.eq("_id",planetId)).first();
    }

    public List<Planet> getPlanets(){
        MongoCollection<Document> collection = database.getCollection(Collection.PLANETS.toString());

        List<Planet> planets = new LinkedList<>();
        for( Document planet : collection.find() ){
            planets.add(Planet.fromDocument(planet));
        }

        return planets;
    }



    public void addTrack(Track track){
        MongoCollection<Document> collection = database.getCollection( Collection.TRACKS.toString());
        collection.insertOne(track.toDocument() );
    }



    public Track getTrack(int trackId) {
        MongoCollection<Document> trackCollection = database.getCollection( Collection.TRACKS.toString() );

        Document result = trackCollection.find(Filters.eq("_id", trackId)).first();

        if( result == null ) return null;

        return Track.fromDocument(result);
    }




    public void addTrackBlockData(int trackId, byte[] trackData){
        MongoCollection<Document> collection = database.getCollection(Collection.TRACKDATA.toString());

        Document object = new Document()
                .append("_id", trackId)
                // Stores the binary as base65 encoded (consider doing this manually)
                .append("data", trackData);//new Binary(trackData));

        collection.insertOne(object);
    }


    public byte[] getTrackData(int trackId){
        MongoCollection<Document> collection = database.getCollection(Collection.TRACKDATA.toString());

        Document result = collection.find(Filters.eq("_id", trackId)).first();

        if( result == null){
            System.out.println("Result is null");
            return null;
        }


        // This retrieves the bytes correctly
        byte[] bytes = ((Binary) result.get("data")).getData();

        return bytes;
    }


    public void addRound(Round round){
        MongoCollection<Document> collection = database.getCollection(Collection.ROUNDS.toString());
        collection.insertOne(round.toDocument());
    }



    public List<Round> getRounds(){
        MongoCollection<Document> collection = database.getCollection(Collection.ROUNDS.toString());

        List<Round> rounds = new LinkedList<>();
        for( Document round : collection.find() )
            rounds.add(Round.fromDocument(round));

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


    private static MongoClient createClient(){
        return MongoClients.create("mongodb+srv://game:deepflightisawesome@deepflight-cu0et.mongodb.net/test?retryWrites=true&w=majority");
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
        MongoClient client = createClient();
        client.getDatabase(DB_NAME + "_test").drop();
        client.close();
        setupTestDatabase();
    }

    /** Adds test data to test database */
    private static void setupTestDatabase() throws UnknownHostException {
        DatabaseConnector db = new DatabaseConnector();
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
    }

}

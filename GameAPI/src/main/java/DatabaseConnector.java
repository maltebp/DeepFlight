
import com.mongodb.*;
import model.Planet;
import org.bson.types.Binary;

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


    public List<Planet> getPlanets(){
        DBCollection collection = database.getCollection(Collection.PLANETS.toString());

        List<Planet> planets = new LinkedList<>();
        collection.find().forEach( (object) -> {
            //TODO: Fix this try catch
            try {
                planets.add(Planet.fromMongoObject(object));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return planets;
    }



    public void saveTrackBlockData(int trackId, byte[] trackData){

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

        if( result == null) System.out.println("Result is null");
        //byte[] bytes = result.getString("data").getBytes();


        // This retrieves the bytes correctly
        byte[] bytes = (byte[]) result.get("data");


        return bytes;

        //return bytes;
    }


    public void close(){
        if( client != null ) {
            // Closes client and database
            client.close();
            client = null;
        }
    }



    // -----------------------------------------------------------------------------------------------
    // TEST MODE

    public static void enableTestMode() throws UnknownHostException {
        testMode = true;
        MongoClient client = new MongoClient(new MongoClientURI(DB_SERVER_URL));
        client.dropDatabase(DB_NAME + "_test");
        client.close();
    }


    public enum Collection {

        PLANETS("planets"),
        TRACKDATA("trackdata");

        private final String name;

        Collection(String name){ this.name = name; }

        @Override
        public String toString() { return name; }
    }

}

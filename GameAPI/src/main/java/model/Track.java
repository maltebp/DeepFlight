package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONObject;

import java.io.IOException;

public class Track {

    private int id;

    private String name;

    private int planetId;

    private int length;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPlanetId() {
        return planetId;
    }

    public int getLength() {
        return length;
    }

    // Default constructor is required for JSON serialization
    public Track(){ }

    public Track(int id, String name, int planetId, int length) {
        this.id = id;
        this.name = name;
        this.planetId = planetId;
        this.length = length;
    }

    public static Planet fromMongoObject(DBObject object) throws IOException {
        JSONObject jsonObject = new JSONObject(JSON.serialize(object));
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");
        return new ObjectMapper().readValue(jsonObject.toString(), Planet.class);
    }

    public DBObject toMongoObject(){
        JSONObject json = new JSONObject(this);
        json.put("_id", id);
        json.remove("id");
        return (DBObject) JSON.parse(json.toString());
    }


}

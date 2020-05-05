package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Track {

    private String id;

    private String name;

    private String planetId;

    private HashMap<String, Integer> times;

    // Default constructor is required for JSON serialization
    public Track(){ }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanetId() {
        return planetId;
    }

    public void setPlanetId(String planetId) {
        this.planetId = planetId;
    }

    public HashMap<String, Integer> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String, Integer> times) {
        this.times = times;
    }

    public JSONObject toJSON() throws JsonProcessingException {
        return new JSONObject(new ObjectMapper().writeValueAsString(this));
    }


    public static Track fromDocument(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");

        try{
            return new ObjectMapper().readValue(jsonObject.toString(), Track.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Document toDocument(){
        JSONObject json = new JSONObject(this);
        json.put("_id", id);
        json.remove("id");
        return Document.parse(json.toString());
    }


}

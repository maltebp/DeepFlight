package model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;


public class Planet {

    private String id;
    private String name;
    private int[] color;

    // Default constructor must exist for JSON deserialize to work
    public Planet() {}

    public Planet(String id, String name, int[] color) {
        this.id = id;
        this.name = name;
        this.color = color;

        if( color.length != 3)
            throw new IllegalArgumentException("Color array must be length 3");
    }

    // Getters required for JSON serialization
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int[] getColor() {
        return color;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setColor(int[] color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + Arrays.toString(color) +
                '}';
    }

    public JSONObject toJSON() throws JsonProcessingException {
        return new JSONObject(new ObjectMapper().writeValueAsString(this));
    }

    public static Planet fromMongoObject(DBObject object) {
        JSONObject jsonObject = new JSONObject(JSON.serialize(object));
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");

        try{
            return new ObjectMapper().readValue(jsonObject.toString(), Planet.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public DBObject toMongoObject(){
        JSONObject json = new JSONObject(this);
        json.put("_id", id);
        json.remove("id");
        return (DBObject) JSON.parse(json.toString());
    }

    public static Planet fromDocument(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");

        try{
            return new ObjectMapper().readValue(jsonObject.toString(), Planet.class);
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

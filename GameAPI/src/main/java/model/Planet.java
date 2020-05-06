package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Arrays;


import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Field;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Index;
import dev.morphia.annotations.Indexes;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;
import dev.morphia.query.UpdateResults;

@Entity("planets")
public class Planet {
    @Id
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

    public JSONObject toJSON() {
        return new JSONObject(this);
    }


}

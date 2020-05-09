package model;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import java.util.Arrays;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

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

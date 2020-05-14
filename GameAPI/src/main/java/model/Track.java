package model;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity("tracks")

public class Track {
    @Id
    private String id;

    private String name;

    private String planetId;

    private String seed;

    private HashMap<String,Integer> times;

    // region Getters and Setters

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

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public HashMap<String, Integer> getTimes() {
        return times;
    }

    public void setTimes(HashMap<String, Integer> times) {
        this.times = times;
    }

    // endregion

    public JSONObject toJSON() {
        return new JSONObject(this);
    }

}

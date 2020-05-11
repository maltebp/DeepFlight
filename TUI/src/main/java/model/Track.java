package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Track {
    private String id;
    private String name;
    private String planetId;
    private String seed;
    private HashMap<String,Integer> times;

    public Track () {}

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

    public String toString () {

        StringBuilder builder = new StringBuilder();
        builder.append("Track{");

        builder.append(String.format("id=%s",id));
        builder.append(String.format(", name=%s",name));
        builder.append(String.format(", planetId=%s",planetId));
        builder.append(String.format(", seed=%s",seed));
        if (times != null) {
            builder.append(", times{");
            for (Map.Entry<String, Integer> entry : times.entrySet()){
                builder.append(String.format("%s=%d, ",entry.getKey(),entry.getValue()));
            }
            builder.append("}");
        }

        builder.append("}");
        return builder.toString();
    }

}

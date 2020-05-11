package model;

import java.util.Arrays;

public class Planet {
    private String id;
    private String name;
    private int[] color;

    public Planet() {}

    // region Getter and Setters

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

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }


    // endregion

    @Override
    public String toString() {
        return "Planet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + Arrays.toString(color) +
                '}';
    }

}

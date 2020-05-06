package model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity("users")
public class User {
    @Id
    private String id;
    private String username;
    private int rank; // This is universal rank
    private double rating; // This is universal rating

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", rank=" + rank +
                ", rating=" + rating +
                '}';
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject(this);
        return new JSONObject(this);
    }
}

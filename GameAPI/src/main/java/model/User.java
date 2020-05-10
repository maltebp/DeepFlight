package model;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity("users")
@Indexes(@Index(fields = @Field(value = "$**", type = IndexType.TEXT)))
public class User {
    @Id
    private String id;

    private String username;
    private int rank; // This is universal rank
    private double rating; // This is universal rating

    public User (String username){
        this.username = username;
        rank = 0;
        rating = 0;
    }

    // region Properties

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

    // endregion

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

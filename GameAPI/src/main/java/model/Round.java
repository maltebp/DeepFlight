package model;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Arrays;

public class Round {

    private int roundNumber;

    private Track[] tracks[] = null;

    private Date startDate;
    private Date endDate;





    public DBObject toMongoObject(){
        JSONObject json = new JSONObject(this);
        json.put("_id", roundNumber);
        json.remove("roundNumber");
        return (DBObject) JSON.parse(json.toString());
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                ", tracks=" + Arrays.toString(tracks) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
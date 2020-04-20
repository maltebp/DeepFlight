package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

public class Round {

    // Also used for ID
    private int roundNumber;

    private int[] trackIds = null;

    private long startDate;

    private long endDate;

    // Public default constructor required for JSON serialization
    public Round() { }

    public Round(int roundNumber, int[] trackIds, long startDate, long endDate) {
        this.roundNumber = roundNumber;
        this.trackIds = trackIds;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int[] getTrackIds() {
        return trackIds;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                ", trackIds=" + Arrays.toString(trackIds) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public DBObject toMongoObject(){
        JSONObject json = new JSONObject(this);
        json.put("_id", roundNumber);
        json.remove("roundNumber");
        return (DBObject) JSON.parse(json.toString());
    }


    public static Round fromMongoObject(DBObject object) throws IOException {
        JSONObject jsonObject = new JSONObject(JSON.serialize(object));
        jsonObject.put("roundNumber", jsonObject.get("_id"));
        jsonObject.remove("_id");
        return new ObjectMapper().readValue(jsonObject.toString(), Round.class);
    }

}
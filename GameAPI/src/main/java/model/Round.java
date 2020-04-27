package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonWriterSettings;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public JSONObject toJSON() {
        return new JSONObject(this);
    }


    public Document toDocument(){
        JSONObject json = new JSONObject(this);
        json.put("_id", roundNumber);
        json.remove("roundNumber");
        return Document.parse(json.toString());
    }


    public static Round fromDocument(Document document){
        JSONObject baseJson = new JSONObject(document.toJson());

        JSONObject adjustedJson = new JSONObject();
        adjustedJson.put("roundNumber", baseJson.getInt("_id"));
        adjustedJson.put("trackIds", baseJson.get("trackIds"));
        adjustedJson.put("startDate", document.getLong("startDate"));
        adjustedJson.put("endDate", document.getLong("endDate"));

        try {
            return new ObjectMapper().readValue(adjustedJson.toString(), Round.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
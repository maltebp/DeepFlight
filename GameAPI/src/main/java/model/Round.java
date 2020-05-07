package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.morphia.annotations.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
@Entity("rounds")
@Indexes({
        @Index(
                fields = @Field("startDate"),
                options = @IndexOptions(name = "round_startDate")
        )
})
public class Round {
    @Id
    private ObjectId id;

    // Also used for ID
    private int roundNumber;

    private String[] trackIds = null;

    private long startDate;

    private long endDate;

    private HashMap<String, Double> rankings;

    // Public default constructor required for JSON serialization
    public Round() { }

    public Round(int roundNumber, String[] trackIds, long startDate, long endDate) {
        this.roundNumber = roundNumber;
        this.trackIds = trackIds;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String[] getTrackIds() {
        return trackIds;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setRankings(HashMap<String, Double> rankings) {
        this.rankings = rankings;
    }

    public HashMap<String, Double> getRankings() {
        return rankings;
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
}
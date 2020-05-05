package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Round {

    private String id;

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

    public String getId() {
        return id;
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


    public Document toDocument(){
        JSONObject json = new JSONObject(this);
        json.put("_id", roundNumber);
        json.remove("roundNumber");
        return Document.parse(json.toString());
    }


    public static Round fromDocument(Document document){
        JSONObject baseJson = new JSONObject(document.toJson());

        JSONObject adjustedJson = new JSONObject();
        adjustedJson.put("roundNumber", baseJson.get("_id"));
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


    // TODO: Remove this!
    public static void main(String[] args) {
        Round round = new Round();
        round.id = "thisisanid";
        round.trackIds = new String[]{"1", "2", "3", "4"};
        round.roundNumber = 99;
        round.startDate = 10000000;
        round.endDate = 20000000;

        HashMap<String, Double> rankings = new HashMap<>();
        rankings.put("Malte", 1.234);
        rankings.put("Andreas", 3.455);

        round.rankings = rankings;

        System.out.println(round.toJSON());

        List<Round> rounds = new ArrayList<>();
        rounds.add(round);
        rounds.add(round);
        rounds.add(round);


        JSONArray roundsJson = new JSONArray();
        for( Round thisRound : rounds ){
            roundsJson.put(thisRound.toJSON());
        }

        System.out.println(roundsJson);

    }

}
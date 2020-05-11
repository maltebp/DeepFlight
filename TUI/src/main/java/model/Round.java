package model;


import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class Round {
    private String id;
    private int roundNumber;
    private String[] trackIds = null;
    private long startDate;
    private long endDate;
    private HashMap<String, Double> rankings;

    public Round() { }

    // region Getters and Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String[] getTrackIds() {
        return trackIds;
    }

    public void setTrackIds(String[] trackIds) {
        this.trackIds = trackIds;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public HashMap<String, Double> getRankings() {
        return rankings;
    }

    public void setRankings(HashMap<String, Double> rankings) {
        this.rankings = rankings;
    }

    // endregion

    @Override
    public String toString() {
        return "Round{" +
                "roundNumber=" + roundNumber +
                ", trackIds=" + Arrays.toString(trackIds) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
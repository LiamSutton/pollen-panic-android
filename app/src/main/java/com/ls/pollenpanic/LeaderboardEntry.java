package com.ls.pollenpanic;

// encapsulates the information required for a leaderboard submission
public class LeaderboardEntry {

    private int id;
    private String username;
    private int score;

    public LeaderboardEntry(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

}

package com.ls.pollenpanic;

// used to model a leaderboard entry
public class ScoreModel {
    private int score;
    private String userName;

    public int getScore() {
        return score;
    }

    public String getUserName() {return userName; }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUserName(String userName) {this.userName = userName; }
}

package com.example.l.rBOOK.model.dto;

public class User {
    private String userAccount;
    private String userPassword;
    private String userUsername;
    private int total;
    private int rankStatus;

    public User() {

    }

    public User(String userAccount, String userUsername) {
        this.userAccount = userAccount;
        this.userUsername = userUsername;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public int getRankStatus() {
        return rankStatus;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setRankStatus(int rankStatus) {
        this.rankStatus = rankStatus;
    }
}

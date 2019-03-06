package com.example.l.rBOOK.model.dto;

public class UserDTO {
    private int userId;
    private String userUsername;

    public UserDTO(int userId, String userUsername) {
        this.userId = userId;
        this.userUsername = userUsername;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}

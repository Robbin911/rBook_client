package com.example.l.rBOOK.model.dto;

public class GroupResInfo {
    private String counter;
    private String counterNickname;
    private int num;
    private boolean myStatus;
    private boolean counterStatus;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCounterStatus() {
        return counterStatus;
    }

    public boolean isMyStatus() {
        return myStatus;
    }

    public String getCounter() {
        return counter;
    }

    public String getCounterNickname() {
        return counterNickname;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setCounterNickname(String counterNickname) {
        this.counterNickname = counterNickname;
    }

    public void setCounterStatus(boolean counterStatus) {
        this.counterStatus = counterStatus;
    }

    public void setMyStatus(boolean myStatus) {
        this.myStatus = myStatus;
    }
}

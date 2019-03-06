package com.example.l.rBOOK.model.dto;

import java.time.LocalDate;

public class Group {
    private String uuid;
    private String groupName;
    private int memberNum;
    private String date;
    private int status;
    private boolean myConfirm;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isMyConfirm() {
        return myConfirm;
    }

    public void setMyConfirm(boolean myConfirm) {
        this.myConfirm = myConfirm;
    }
}

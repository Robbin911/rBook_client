package com.example.l.rBOOK.model.dto;


public class GroupDetail {
    private String uuid;
    private String desc;
    private int num;
    private String date;
    private boolean propose; // true if user proposes it , false if not
    private String proposeName;
    private String proposeNickName;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getNum() {
        return num;
    }

    public boolean isPropose() {
        return propose;
    }

    public String getDate() {
        return date;
    }

    public String getProposeName() {
        return proposeName;
    }

    public String getProposeNickName() {
        return proposeNickName;
    }

    public void setPropose(boolean propose) {
        this.propose = propose;
    }

    public void setProposeName(String proposeName) {
        this.proposeName = proposeName;
    }

    public void setProposeNickName(String proposeNickName) {
        this.proposeNickName = proposeNickName;
    }

}

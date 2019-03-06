package com.example.l.rBOOK.model.dto;

import java.time.LocalDate;

public class DebtTotal {
    private String name;
    private String account;
    private int totalNum;
    private boolean unread;
    private String date;

    public DebtTotal(){}
    public DebtTotal(String userName,String account,int totalNum,boolean unread,String date){
        this.name=userName;
        this.account=account;
        this.totalNum=totalNum;
        this.unread=unread;
        this.date=date;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public String getDate() {
        return date;
    }

    public String getName() { return name; }

    public String getAccount() { return account; }

    public boolean isUnread() {
        return unread;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public void setAccount(String account) { this.account = account; }
}

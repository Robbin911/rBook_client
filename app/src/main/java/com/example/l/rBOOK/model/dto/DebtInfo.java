package com.example.l.rBOOK.model.dto;

import java.time.LocalDate;

public class DebtInfo {
    private String id;
    private int num;
    private String date;
    private String desc;
    private int status;						//0-normal , 1-wait to add, 2-wait to delete, 3-wait to combine,4-combined
    private String start;
    private String end;
    private boolean proposal;

    public DebtInfo(){

    }
    public DebtInfo(String id,int num,String date,String desc,int status,String start,String end,boolean proposal){
        this.id=id;
        this.num=num;
        this.date=date;
        this.desc=desc;
        this.status=status;
        this.start=start;
        this.end=end;
        this.proposal=proposal;
    }

    public boolean isProposal() { return proposal; }

    public void setDate(String date) {
        this.date = date;
    }

    public String  getDate() {
        return date;
    }

    public int getNum() { return num; }

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getEnd() { return end; }

    public String getStart() {
        return start;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEnd(String end) { this.end = end; }

    public void setId(String id) {
        this.id = id;
    }

    public void setNum(int num) { this.num = num; }

    public void setStart(String start) {
        this.start = start;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setProposal(boolean proposal) { this.proposal = proposal; }
}
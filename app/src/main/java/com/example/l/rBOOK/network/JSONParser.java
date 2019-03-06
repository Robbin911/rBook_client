package com.example.l.rBOOK.network;


import com.example.l.rBOOK.model.dto.DebtInfo;
import com.example.l.rBOOK.model.dto.DebtTotal;
import com.example.l.rBOOK.model.dto.Group;
import com.example.l.rBOOK.model.dto.GroupDetail;
import com.example.l.rBOOK.model.dto.GroupResInfo;
import com.example.l.rBOOK.model.dto.LoopCheck;
import com.example.l.rBOOK.model.dto.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JSONParser {

    public static User parseJsonToUser(JSONObject jsonObject) {
        User user = null;
        try {
            JSONObject userData = jsonObject.getJSONObject("data");
            user = new User(userData.getString("username"), userData.getString("nickname"));
            user.setRankStatus(userData.getInt("rankStatus"));
            user.setTotal(userData.getInt("totalAccount"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public static List<DebtTotal> parseJSONToDebtTotalList(JSONObject jsonObject){
        List<DebtTotal> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                DebtTotal info=new DebtTotal();
                info.setName(dataObject.getString("nickname"));
                info.setAccount(dataObject.getString("name"));
                info.setUnread(dataObject.getBoolean("unread"));
                info.setTotalNum(dataObject.getInt("totalNum"));
                info.setDate(dataObject.getString("date"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<DebtInfo> parseJSONToDebtDetailList(JSONObject jsonObject){
        List<DebtInfo> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                DebtInfo info=new DebtInfo();
                info.setId(dataObject.getString("uuid"));
                info.setNum(dataObject.getInt("num"));
                info.setDate(dataObject.getString("date"));
                info.setDesc(dataObject.getString("desc"));
                info.setStatus(dataObject.getInt("status"));
                info.setStart(dataObject.getString("start"));
                info.setEnd(dataObject.getString("end"));
                info.setProposal(dataObject.getBoolean("proposal"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();

        }
        return list;
    }

    public static List<Group> parseJSONToGroupList(JSONObject jsonObject){
        List<Group> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                Group info=new Group();
                info.setUuid(dataObject.getString("uuid"));
                info.setGroupName(dataObject.getString("name"));
                info.setMemberNum(dataObject.getInt("member"));
                info.setDate(dataObject.getString("updateTime"));
                info.setStatus(dataObject.getInt("status"));
                info.setMyConfirm(dataObject.getBoolean("myConfirm"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<GroupDetail> parseJSONToGroupDebtList(JSONObject jsonObject){
        List<GroupDetail> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                GroupDetail info=new GroupDetail();
                info.setUuid(dataObject.getString("uuid"));
                info.setDesc(dataObject.getString("desc"));
                info.setNum(dataObject.getInt("num"));
                info.setDate(dataObject.getString("time"));
                info.setPropose(dataObject.getBoolean("propose"));
                info.setProposeName(dataObject.getString("proposeName"));
                info.setProposeNickName(dataObject.getString("proposeNickName"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<User> parseJSONToGroupMemberList(JSONObject jsonObject){
        List<User> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                User info=new User();
                info.setUserAccount(dataObject.getString("username"));
                info.setUserUsername(dataObject.getString("nickname"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }
    public static List<User> parseJSONToGroupDebtMemberList(JSONObject jsonObject){
        List<User> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                User info=new User();
                info.setUserAccount(dataObject.getString("username"));
                info.setUserUsername(dataObject.getString("nickname"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<GroupResInfo> parseJSONToGroupResList(JSONObject jsonObject){
        List<GroupResInfo> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                GroupResInfo info=new GroupResInfo();
                info.setUuid(dataObject.getString("uuid"));
                info.setCounter(dataObject.getString("counter"));
                info.setCounterNickname(dataObject.getString("counterNickname"));
                info.setNum(dataObject.getInt("num"));
                info.setMyStatus(dataObject.getBoolean("myStatus"));
                info.setCounterStatus(dataObject.getBoolean("counterStatus"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static Group parseJsonToGroupInfo(JSONObject jsonObject) {
        Group group =new Group();
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            group.setMyConfirm(data.getBoolean("myConfirm"));
            group.setStatus(data.getInt("status"));
            group.setDate(data.getString("updateTime"));
            group.setMemberNum(data.getInt("member"));
            group.setUuid(data.getString("uuid"));
            group.setGroupName(data.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    public static List<LoopCheck> parseJSONToLoopList(JSONObject jsonObject){
        List<LoopCheck> list=new ArrayList<>();
        try{
            String data=jsonObject.getString("data");
            JSONArray dataList=new JSONArray(data);
            for(int j=0;j<dataList.length();j++){
                JSONObject dataObject=dataList.getJSONObject(j);
                LoopCheck info=new LoopCheck();
                info.setEnd(dataObject.getString("end"));
                info.setStart(dataObject.getString("start"));
                info.setScale(dataObject.getInt("scale"));
                list.add(info);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

}

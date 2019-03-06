package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.model.DebtDetailList;
import com.example.l.rBOOK.model.DebtDetailListInterface;
import com.example.l.rBOOK.model.GroupDetailList;
import com.example.l.rBOOK.model.GroupDetailListInterface;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.DebtDetailPageInterface;
import com.example.l.rBOOK.view.GroupDetailPageInterface;

import org.json.JSONObject;

import java.util.Map;

public class GroupDetailPresenter implements GroupDetailPresenterInterface,GroupDetailList.GroupDetailDataCallback,GroupDetailList.GroupDetailRequestCallback {
    private final GroupDetailPageInterface groupDetailPageInterface;
    private final GroupDetailListInterface getDebtDetail;


    public GroupDetailPresenter(GroupDetailPageInterface view){
        this.getDebtDetail=new GroupDetailList();
        this.groupDetailPageInterface = view;
    }


    @Override
    public void updateGroupInfo(Context context, Map<String, Object> map){
        getDebtDetail.getGroupInfo(this,context,map);
    }
    @Override
    public void getGroupDebtList(Context context, Map<String, Object> map){
        getDebtDetail.getGroupDebtData(this,context,map);
    }

    @Override
    public void getGroupDebtMemberList(Context context,Map<String, Object> map){
        getDebtDetail.getGroupDebtMemberData(this,context,map);
    }

    @Override
    public void getGroupMemberList(Context context,Map<String, Object> map){
        getDebtDetail.getGroupMemberData(this,context,map);
    }

    @Override
    public void getGroupAddList(Context context,Map<String, Object> map){
        getDebtDetail.getGroupAddMemberData(this,context,map);
    }

    @Override
    public void getGroupResCheckList(Context context,Map<String, Object> map){

        getDebtDetail.getGroupCheckData(this,context,map);
    }
    @Override
    public void addGroupDebt(Context context,Map<String, Object> map){
        getDebtDetail.sendAddGroupDebtRequest(this,context,map);
    }
    @Override
    public void deleteGroupDebt(Context context,Map<String, Object> map){
        getDebtDetail.sendDeleteGroupDebtRequest(this,context,map);
    }

    @Override
    public void confirmGroup(Context context, Map<String, Object> map){
        getDebtDetail.sendConfirmGroupDebtRequest(this,context,map);
    }
    @Override
    public void checkGroupRes(Context context, Map<String, Object> map){
        getDebtDetail.sendCheckGroupResRequest(this,context,map);

    }


    @Override
    public void onDataSuccess(JSONObject jsonObject,int type){
        if(type==1){
            groupDetailPageInterface.setDebtList(JSONParser.parseJSONToGroupDebtList(jsonObject));
        }
        else if(type==2){
            groupDetailPageInterface.setDebtMemberList(JSONParser.parseJSONToGroupDebtMemberList(jsonObject));
        }
        else if(type==3){
            groupDetailPageInterface.setMemberList(JSONParser.parseJSONToGroupMemberList(jsonObject));
        }
        else if(type==4){
            groupDetailPageInterface.setCheckList(JSONParser.parseJSONToGroupResList(jsonObject));
        }
        else if(type==5){
            groupDetailPageInterface.setAddList(JSONParser.parseJSONToGroupMemberList(jsonObject));
        }
        else if(type==0){
            groupDetailPageInterface.setGroupInfo(JSONParser.parseJsonToGroupInfo(jsonObject));
        }
    }
    @Override
    public void onDataFail() {
        groupDetailPageInterface.showMessage(0);
    }
    @Override
    public void onRequestSuccess(int type) {
        groupDetailPageInterface.showMessage(type);
    }
    @Override
    public void onRequestFail(int type) {
        groupDetailPageInterface.showMessage(type);
    }

}

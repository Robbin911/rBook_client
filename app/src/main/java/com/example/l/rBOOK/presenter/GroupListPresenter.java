package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.model.GroupList;
import com.example.l.rBOOK.model.GroupListInterface;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.GroupListViewInterface;

import org.json.JSONObject;

import java.util.Map;

public class GroupListPresenter implements GroupListPresenterInterface,GroupList.GroupCallback,GroupList.GroupNewCallback {
    private final GroupListViewInterface groupListViewInterface;
    private final GroupListInterface groupList;

    public GroupListPresenter(GroupListViewInterface view){
        this.groupListViewInterface=view;
        this.groupList=new GroupList();
    }

    @Override
    public void loadList(Context context, Map<String, Object> map){
        groupList.getGroupData(this,context,map);
    }

    @Override
    public void sendNewGroupRequest(Context context,Map<String, Object> map){
        groupList.sendNewGroupRequest(this,context,map);
    }
    @Override
    public void sendJoinGroupRequest(Context context,Map<String, Object> map){
        groupList.sendJoinGroupRequest(this,context,map);
    }

    @Override
    public void onGroupSuccess(JSONObject jsonObject) {
        groupListViewInterface.getList(JSONParser.parseJSONToGroupList(jsonObject));
    }

    @Override
    public void onGroupFail() {
        groupListViewInterface.showMessage(4);
    }
    @Override
    public void onGroupNewSuccess(int type){
        groupListViewInterface.showMessage(type);
    }

    public void onGroupNewFail(int type){
        groupListViewInterface.showMessage(type);

    }

}

package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.network.JSONParser;

import java.util.Map;

public interface GroupDetailPresenterInterface {
    void updateGroupInfo(Context context, Map<String, Object> map);
    void getGroupDebtList(Context context, Map<String, Object> map);
    void getGroupDebtMemberList(Context context,Map<String, Object> map);
    void getGroupMemberList(Context context,Map<String, Object> map);
    void getGroupAddList(Context context,Map<String, Object> map);
    void getGroupResCheckList(Context context,Map<String, Object> map);
    void addGroupDebt(Context context,Map<String, Object> map);
    void deleteGroupDebt(Context context,Map<String, Object> map);
    void confirmGroup(Context context, Map<String, Object> map);
    void checkGroupRes(Context context, Map<String, Object> map);
}

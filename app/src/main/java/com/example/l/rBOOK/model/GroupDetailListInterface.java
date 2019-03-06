package com.example.l.rBOOK.model;

import android.content.Context;

import com.example.l.rBOOK.model.dto.GroupDetail;

import java.util.Map;

public interface GroupDetailListInterface {

    void getGroupInfo(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void getGroupDebtData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void getGroupDebtMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void getGroupMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void getGroupAddMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void getGroupCheckData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map);
    void sendAddGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map);
    void sendDeleteGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map);
    void sendConfirmGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map);
    void sendCheckGroupResRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map);
}

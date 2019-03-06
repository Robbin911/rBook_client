package com.example.l.rBOOK.model;

import android.content.Context;

import java.util.Map;

public interface GroupListInterface {
    void getGroupData(final GroupList.GroupCallback callback, final Context context, Map<String, Object> map);
    void sendNewGroupRequest(final GroupList.GroupNewCallback callback,final Context context,Map<String, Object> map);
    void sendJoinGroupRequest(final GroupList.GroupNewCallback callback,final Context context,Map<String, Object> map);
}

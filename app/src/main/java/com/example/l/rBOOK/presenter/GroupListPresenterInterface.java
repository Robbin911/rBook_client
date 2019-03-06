package com.example.l.rBOOK.presenter;

import android.content.Context;

import java.util.Map;

public interface GroupListPresenterInterface {
    void loadList(Context context, Map<String, Object> map);
    void sendNewGroupRequest(Context context,Map<String, Object> map);
    void sendJoinGroupRequest(Context context,Map<String, Object> map);
}

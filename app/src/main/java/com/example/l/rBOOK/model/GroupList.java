package com.example.l.rBOOK.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class GroupList implements GroupListInterface{
    @Override
    public void getGroupData(final GroupCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_list_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onGroupSuccess(jsonObject);
            }
            @Override
            public void onFailure() {
                callback.onGroupFail();
            }
        });
    }

    @Override
    public void sendNewGroupRequest(final GroupNewCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_new_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onGroupNewSuccess(0);
                    } else {
                        callback.onGroupNewFail(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onGroupNewFail(1);
            }
        });
    }
    @Override
    public void sendJoinGroupRequest(final GroupNewCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_join_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onGroupNewSuccess(2);
                    } else {
                        callback.onGroupNewFail(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onGroupNewFail(3);
            }
        });
    }

    public interface GroupCallback {
        void onGroupSuccess(JSONObject jsonObject);
        void onGroupFail();
    }
    public interface GroupNewCallback{
        void onGroupNewSuccess(int type);
        void onGroupNewFail(int type);
    }
}

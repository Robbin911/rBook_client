package com.example.l.rBOOK.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class GroupDetailList implements GroupDetailListInterface{

    @Override
    public void getGroupInfo(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_update_info_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,0);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }
    @Override
    public void getGroupDebtData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_debt_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,1);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }

    @Override
    public void getGroupDebtMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_debt_member_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,2);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }
    @Override
    public void getGroupMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_member_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,3);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }
    @Override
    public void getGroupAddMemberData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_add_sheet_member_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,5);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }

    @Override
    public void getGroupCheckData(final GroupDetailList.GroupDetailDataCallback callback, final Context context, Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_check_list_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDataSuccess(jsonObject,4);
            }
            @Override
            public void onFailure() {
                callback.onDataFail();
            }
        });
    }
    @Override
    public void sendAddGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_add_debt_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onRequestSuccess(1);
                    } else { callback.onRequestFail(0); }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() { callback.onRequestFail(0); }
        });
    }

    @Override
    public void sendDeleteGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_delete_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onRequestSuccess(2);
                    } else { callback.onRequestFail(0); }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() { callback.onRequestFail(0); }
        });
    }
    @Override
    public void sendConfirmGroupDebtRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_confirm_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onRequestSuccess(3);
                    } else { callback.onRequestFail(0); }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() { callback.onRequestFail(0); }
        });
    }
    @Override
    public void sendCheckGroupResRequest(final GroupDetailList.GroupDetailRequestCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.group_check_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onRequestSuccess(4);
                    } else { callback.onRequestFail(0); }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() { callback.onRequestFail(0); }
        });
    }


    public interface GroupDetailDataCallback {
        void onDataSuccess(JSONObject jsonObject,int type);
        void onDataFail();
    }
    public interface GroupDetailRequestCallback{
        void onRequestSuccess(int type);
        void onRequestFail(int type);
    }
}

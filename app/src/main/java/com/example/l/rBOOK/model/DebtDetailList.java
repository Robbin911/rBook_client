package com.example.l.rBOOK.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class DebtDetailList implements DebtDetailListInterface{
    @Override
    public void getDebtDetailData(final DebtDetailCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try{
                    if(jsonObject.getInt("code")==0){
                        int total=jsonObject.getInt("msg");
                        callback.onDebtDetailSuccess(jsonObject,total);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                callback.onDebtDetailFail();
            }
        });

    }
    @Override
    public void addDebt(final DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_add_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onDebtDetailOperationSuccess(1);
                    } else {
                        callback.onDebtDetailOperationFail(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onDebtDetailOperationFail(0);
            }
        });
    }
    @Override
    public void deleteDebt(final DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_delete_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onDebtDetailOperationSuccess(3);
                    } else {
                        callback.onDebtDetailOperationFail(4);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onDebtDetailOperationFail(0);
            }
        });
    }
    @Override
    public void combineDebt(final DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_combine_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onDebtDetailOperationSuccess(5);
                    } else {
                        callback.onDebtDetailOperationFail(6);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onDebtDetailOperationFail(0);
            }
        });
    }
    @Override
    public void acceptConfirm(final DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_accept_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onDebtDetailOperationSuccess(7);
                    } else {
                        callback.onDebtDetailOperationFail(8);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onDebtDetailOperationFail(0);
            }
        });
    }
    @Override
    public void refuseConfirm(final DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_detail_refuse_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onDebtDetailOperationSuccess(9);
                    } else {
                        callback.onDebtDetailOperationFail(10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onDebtDetailOperationFail(0);
            }
        });
    }

    public interface DebtDetailCallback {
        void onDebtDetailSuccess(JSONObject jsonObject,int total);
        void onDebtDetailFail();
    }
    public interface DebtDetailOperationCallBack{
        void onDebtDetailOperationSuccess(int type);
        void onDebtDetailOperationFail(int type);
    }
}

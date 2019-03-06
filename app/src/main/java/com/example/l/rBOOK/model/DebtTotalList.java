package com.example.l.rBOOK.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class DebtTotalList implements DebtTotalListInterface {
    @Override
    public void getDebtTotalData(final DebtTotalCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.debt_total_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onDebtTotalSuccess(jsonObject);
            }
            @Override
            public void onFailure() {
                callback.onDebtTotalFail();
            }
        });
    }

    public void sendAddPairRequest(final DebtAddPairCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.add_pair_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callback.onAddPairSuccess();
                    } else {
                        callback.onAddPairFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure() {
                callback.onAddPairFail();
            }
        });
    }

    public interface DebtTotalCallback {
        void onDebtTotalSuccess(JSONObject jsonObject);
        void onDebtTotalFail();
    }
    public interface DebtAddPairCallback{
        void onAddPairSuccess();
        void onAddPairFail();
    }

}

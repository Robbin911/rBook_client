package com.example.l.rBOOK.model;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class LoopCheckList implements LoopCheckListInterface{

    @Override
    public void getLoopListData(final LoopCallback callback,final Context context,Map<String, Object> map){
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address)+context.getString(R.string.user_loop_check_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                callback.onLoopListSuccess(jsonObject);
            }
            @Override
            public void onFailure() {
                callback.onLoopListFail();
            }
        });
    }


    public interface LoopCallback {
        void onLoopListSuccess(JSONObject jsonObject);
        void onLoopListFail();
    }
}

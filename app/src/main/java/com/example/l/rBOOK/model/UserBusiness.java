package com.example.l.rBOOK.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.network.VolleyCallback;
import com.example.l.rBOOK.network.VolleyRequest;

import org.json.JSONObject;

import java.util.Map;

public class UserBusiness implements UserInterface {
    @Override
    public void login(final LocalCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address) + context.getString(R.string.login_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callBack.onRemoteLoginSuccess(jsonObject);
                    } else {
                        callBack.onRemoteLoginFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                callBack.onRemoteLoginFailure();
            }
        });
    }

    @Override
    public void update(final LocalCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address) + context.getString(R.string.login_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callBack.onUpdateSuccess(jsonObject);
                    } else {
                        callBack.onUpdateFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                callBack.onUpdateFail();
            }
        });
    }


    @Override
    public void registration(final LocalCallBack callBack, Context context, Map<String, Object> map) {
        VolleyRequest.getJSONObject(JsonObjectRequest.Method.POST, context.getString(R.string.server_address) + context.getString(R.string.signup_path), map, context, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject, Context context) {
                try {
                    if (jsonObject.getInt("code") == 0) {
                        callBack.onRemoteRegisterSuccess();
                    } else {
                        callBack.onRemoteRegisterFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure() {
                callBack.onRemoteRegisterFailure();
            }
        });

    }


    public interface LocalCallBack {
        void onRemoteRegisterSuccess();

        void onRemoteRegisterFailure();

        void onRemoteLoginSuccess(JSONObject jsonObject);

        void onRemoteLoginFailure();

        void onUpdateSuccess(JSONObject jsonObject);
        void onUpdateFail();
    }



}

package com.example.l.rBOOK.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class VolleyRequest {
    public static void getJSONObject(int method, String url, Map<String, Object> map, final Context context, final VolleyCallback volleyCallback) {
        try {
            JSONObject params = null;
            if (map != null) {
                params = new JSONObject(map);
            }
            Log.i("VOLLEY", "url: " + url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, params, response -> {
                Log.i("VOLLEY", response.toString());
                volleyCallback.onSuccess(response, context);
            }, error -> {
                Log.e("VOLLEY", error.toString());
                volleyCallback.onFailure();
            });

            VolleyRequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

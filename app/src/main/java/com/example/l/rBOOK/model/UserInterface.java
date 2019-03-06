package com.example.l.rBOOK.model;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Map;

public interface UserInterface {
    void login(final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
    void update(final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
    void registration(final UserBusiness.LocalCallBack callBack, Context context, Map<String, Object> map);
}

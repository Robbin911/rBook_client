package com.example.l.rBOOK.presenter;

import android.content.Context;

import java.util.Map;

public interface DebtTotalPresenterInterface {
    void loadInfoList(Context context,Map<String, Object> map);
    void sendAddPairRequest(Context context,Map<String, Object> map);
}

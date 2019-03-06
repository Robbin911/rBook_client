package com.example.l.rBOOK.model;

import android.content.Context;

import java.util.Map;

public interface LoopCheckListInterface {
    void getLoopListData(final LoopCheckList.LoopCallback callback, final Context context, Map<String, Object> map);
}

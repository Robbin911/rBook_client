package com.example.l.rBOOK.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

public interface DebtDetailPresenterInterface {
    void loadDebtDetailList(Context context,Map<String, Object> map);
    void addDebt(Context context,Map<String, Object> map);
    void deleteDebt(Context context, Map<String, Object> map);
    void combineDebt(Context context,Map<String, Object> map);
    void acceptConfirm(Context context,Map<String, Object> map);
    void refuseConfirm(Context context,Map<String, Object> map);
}

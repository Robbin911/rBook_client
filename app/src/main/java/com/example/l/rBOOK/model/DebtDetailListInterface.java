package com.example.l.rBOOK.model;

import android.content.Context;

import java.util.Map;

public interface DebtDetailListInterface {
    void getDebtDetailData(final DebtDetailList.DebtDetailCallback callback,final Context context,Map<String, Object> map);
    void addDebt(final DebtDetailList.DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map);
    void deleteDebt(final DebtDetailList.DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map);
    void combineDebt(final DebtDetailList.DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map);
    void acceptConfirm(final DebtDetailList.DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map);
    void refuseConfirm(final DebtDetailList.DebtDetailOperationCallBack callback,final Context context,Map<String, Object> map);
}

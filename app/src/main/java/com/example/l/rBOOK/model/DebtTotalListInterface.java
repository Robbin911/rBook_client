package com.example.l.rBOOK.model;

import android.content.Context;

import java.util.Map;

public interface DebtTotalListInterface {
    void getDebtTotalData(final DebtTotalList.DebtTotalCallback callback,final Context context,Map<String, Object> map);

    void sendAddPairRequest(final DebtTotalList.DebtAddPairCallback callback,final Context context,Map<String, Object> map);
}

package com.example.l.rBOOK.view;

import com.example.l.rBOOK.model.dto.DebtInfo;

import java.util.List;

public interface DebtDetailPageInterface {
    void getDebtDetailList(List<DebtInfo> list,int total);
    void showMassage(int type);
}

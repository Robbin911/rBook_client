package com.example.l.rBOOK.view;

import com.example.l.rBOOK.model.dto.DebtTotal;

import java.util.List;

public interface DebtListViewInterface {
    void initList(List<DebtTotal> list);
    void updateList(List<DebtTotal> list);
    void showMessage(int type);
}

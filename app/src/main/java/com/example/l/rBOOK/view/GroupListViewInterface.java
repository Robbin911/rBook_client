package com.example.l.rBOOK.view;

import com.example.l.rBOOK.model.dto.DebtTotal;
import com.example.l.rBOOK.model.dto.Group;

import java.util.List;

public interface GroupListViewInterface {
    void getList(List<Group> list);
    void showMessage(int type);
}

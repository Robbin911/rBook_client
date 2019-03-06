package com.example.l.rBOOK.view;

import com.example.l.rBOOK.model.dto.Group;
import com.example.l.rBOOK.model.dto.GroupDetail;
import com.example.l.rBOOK.model.dto.GroupResInfo;
import com.example.l.rBOOK.model.dto.User;

import java.util.List;

public interface GroupDetailPageInterface {
    void setGroupInfo(Group group);
    void setDebtList(List<GroupDetail> list);
    void setDebtMemberList(List<User> list);
    void setMemberList(List<User> list);
    void setAddList(List<User> list);
    void setCheckList(List<GroupResInfo> list);
    void showMessage(int type);
}

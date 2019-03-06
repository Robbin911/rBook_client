package com.example.l.rBOOK.view;

import com.example.l.rBOOK.model.dto.LoopCheck;
import com.example.l.rBOOK.model.dto.User;

import java.util.List;

public interface WelcomeViewInterface {
    void getLoopList(List<LoopCheck> list);
    void showMessage();
    void updateUser(User user);
}

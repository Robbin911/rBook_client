package com.example.l.rBOOK.view;


import com.example.l.rBOOK.model.dto.User;

public interface LoginViewInterface {
    String getAccount();
    String getPassword();
    void clearUserId();
    void clearPassword();
    void enterMainActivity(User user);
    void showFail();
}

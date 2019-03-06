package com.example.l.rBOOK.model;

import com.example.l.rBOOK.model.dto.User;

public interface OnLoginListener {
    void loginSuccess(User user);
    void loginFail();
}

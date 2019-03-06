package com.example.l.rBOOK.view;


import com.example.l.rBOOK.model.dto.User;

public interface RegistrationViewInterface {
    String getAccount();
    String getPassword();
    void registrationSuccess();
    void registrationFail();
}

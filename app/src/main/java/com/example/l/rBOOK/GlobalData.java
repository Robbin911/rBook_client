package com.example.l.rBOOK;

import android.app.Application;

import com.example.l.rBOOK.model.dto.User;
import com.example.l.rBOOK.presenter.LoginPresenter;

import java.util.HashMap;
import java.util.Map;

public class GlobalData extends Application {
    private User primaryUser;
    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }


}

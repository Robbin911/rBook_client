package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.model.OnLoginListener;
import com.example.l.rBOOK.model.UserBusiness;
import com.example.l.rBOOK.model.UserInterface;
import com.example.l.rBOOK.model.dto.User;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.LoginViewInterface;
import com.example.l.rBOOK.view.WelcomeFragment;
import com.example.l.rBOOK.view.WelcomeViewInterface;

import org.json.JSONObject;

import java.util.Map;


public class LoginPresenter implements UserBusiness.LocalCallBack {
    private UserInterface userBusiness;
    private LoginViewInterface loginView;
    private WelcomeViewInterface welcomeViewInterface;

    public LoginPresenter(LoginViewInterface loginView) {
        this.loginView = loginView;
        this.userBusiness = new UserBusiness();
    }

    public LoginPresenter(WelcomeViewInterface view) {
        this.welcomeViewInterface = view;
        this.userBusiness = new UserBusiness();
    }
    public void Login(Context context, Map<String, Object> map) {
        userBusiness.login(this, context, map);
    }

    public void updateUserData(Context context, Map<String, Object> map) {
        userBusiness.update(this, context, map);
    }

    public void clear() {
        loginView.clearUserId();
        loginView.clearPassword();
    }

    @Override
    public void onRemoteRegisterFailure() {

    }

    @Override
    public void onRemoteRegisterSuccess() {

    }

    @Override
    public void onRemoteLoginSuccess(JSONObject jsonObject) {
        loginView.enterMainActivity(JSONParser.parseJsonToUser(jsonObject));
    }

    @Override
    public void onRemoteLoginFailure() {
        loginView.showFail();
    }

    @Override
    public void onUpdateSuccess(JSONObject jsonObject) {
        welcomeViewInterface.updateUser(JSONParser.parseJsonToUser(jsonObject));
    }

    @Override
    public void onUpdateFail() {

    }
}

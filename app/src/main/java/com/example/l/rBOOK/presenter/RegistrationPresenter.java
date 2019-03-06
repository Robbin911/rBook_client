package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.model.OnRegistrationListener;
import com.example.l.rBOOK.model.UserBusiness;
import com.example.l.rBOOK.model.UserInterface;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.RegistrationViewInterface;

import org.json.JSONObject;

import java.util.Map;

public class RegistrationPresenter implements UserBusiness.LocalCallBack {
    private UserInterface userBusiness;
    private RegistrationViewInterface registrationView;
    public RegistrationPresenter(RegistrationViewInterface registrationView){
        this.registrationView=registrationView;
        this.userBusiness=new UserBusiness();
    }
    public void registration(Context context, Map<String, Object> map){
        userBusiness.registration(this,  context, map);
    }

    @Override
    public void onRemoteRegisterSuccess() {
        registrationView.registrationSuccess();
    }

    @Override
    public void onRemoteRegisterFailure() {
        registrationView.registrationFail();
    }

    @Override
    public void onRemoteLoginSuccess(JSONObject jsonObject) {

    }
    @Override
    public void onRemoteLoginFailure() {

    }
    @Override
    public void onUpdateSuccess(JSONObject jsonObject){
    }
    @Override
    public void onUpdateFail(){

    }
}

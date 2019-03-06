package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.model.GroupDetailListInterface;
import com.example.l.rBOOK.model.LoopCheckList;
import com.example.l.rBOOK.model.LoopCheckListInterface;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.GroupDetailPageInterface;
import com.example.l.rBOOK.view.WelcomeViewInterface;

import org.json.JSONObject;

import java.util.Map;

public class WelcomePresenter implements LoopCheckList.LoopCallback,WelcomePresenterInterface {
    private final WelcomeViewInterface welcomeViewInterface;
    private final LoopCheckListInterface loopCheckList;

    public WelcomePresenter(WelcomeViewInterface view){
        this.welcomeViewInterface=view;
        loopCheckList=new LoopCheckList();
    }

    @Override
    public void getLoopList(Context context, Map<String, Object> map){
        loopCheckList.getLoopListData(this,context,map);
    }

    @Override
    public void onLoopListSuccess(JSONObject jsonObject){
        welcomeViewInterface.getLoopList(JSONParser.parseJSONToLoopList(jsonObject));
    }
    @Override
    public void onLoopListFail(){
        welcomeViewInterface.showMessage();
    }


}

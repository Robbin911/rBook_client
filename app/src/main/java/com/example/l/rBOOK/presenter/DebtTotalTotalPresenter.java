package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.model.DebtTotalList;
import com.example.l.rBOOK.model.DebtTotalListInterface;
import com.example.l.rBOOK.model.dto.DebtTotal;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.DebtListViewInterface;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DebtTotalTotalPresenter implements DebtTotalPresenterInterface,DebtTotalList.DebtTotalCallback,DebtTotalList.DebtAddPairCallback {
    private final DebtListViewInterface debtListViewInterface;
    private final DebtTotalListInterface debtTotalList;


    public DebtTotalTotalPresenter(DebtListViewInterface debtListViewInterface){
        this.debtTotalList=new DebtTotalList();
        this.debtListViewInterface = debtListViewInterface;
    }

    @Override
    public void loadInfoList(Context context,Map<String, Object> map){
        debtTotalList.getDebtTotalData(this,context,map);
    }

    @Override
    public void sendAddPairRequest(Context context,Map<String, Object> map){
        debtTotalList.sendAddPairRequest(this,context,map);
    }


    @Override
    public void onDebtTotalSuccess(JSONObject jsonObject){
        debtListViewInterface.initList(JSONParser.parseJSONToDebtTotalList(jsonObject));

    }
    @Override
    public void onDebtTotalFail(){

        debtListViewInterface.showMessage(0);
    }
    @Override
    public void onAddPairSuccess(){

        debtListViewInterface.showMessage(1);
    }
    @Override
    public void onAddPairFail(){

        debtListViewInterface.showMessage(2);

    }
}

package com.example.l.rBOOK.presenter;

import android.content.Context;

import com.example.l.rBOOK.model.DebtDetailList;
import com.example.l.rBOOK.model.DebtDetailListInterface;
import com.example.l.rBOOK.model.DebtTotalList;
import com.example.l.rBOOK.model.dto.DebtInfo;
import com.example.l.rBOOK.model.dto.DebtTotal;
import com.example.l.rBOOK.network.JSONParser;
import com.example.l.rBOOK.view.DebtDetailPageInterface;
import com.example.l.rBOOK.view.DebtListViewInterface;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DebtDetailPresenter implements DebtDetailPresenterInterface,DebtDetailList.DebtDetailCallback,DebtDetailList.DebtDetailOperationCallBack{
    private final DebtDetailPageInterface debtListViewInterface;
    private final DebtDetailListInterface debtDetailList;


    public DebtDetailPresenter(DebtDetailPageInterface debtDetailPageInterface){
        this.debtDetailList=new DebtDetailList();
        this.debtListViewInterface = debtDetailPageInterface;
    }

    @Override
    public void loadDebtDetailList(Context context,Map<String, Object> map){
        debtDetailList.getDebtDetailData(this,context,map);
    }

    @Override
    public void addDebt(Context context,Map<String, Object> map){
        debtDetailList.addDebt(this,context,map);
    }

    @Override
    public void deleteDebt(Context context,Map<String, Object> map){
        debtDetailList.deleteDebt(this,context,map);
    }
    @Override
    public void combineDebt(Context context,Map<String, Object> map){

        debtDetailList.combineDebt(this,context,map);
    }
    @Override
    public void acceptConfirm(Context context,Map<String, Object> map){
        debtDetailList.acceptConfirm(this,context,map);
    }
    @Override
    public void refuseConfirm(Context context,Map<String, Object> map){
        debtDetailList.refuseConfirm(this,context,map);
    }

    @Override
    public void onDebtDetailSuccess(JSONObject jsonObject,int total){
        debtListViewInterface.getDebtDetailList(JSONParser.parseJSONToDebtDetailList(jsonObject),total);
    }
    @Override
    public void onDebtDetailFail(){
        debtListViewInterface.showMassage(11);

    }

    @Override
    public void onDebtDetailOperationSuccess(int type){
        debtListViewInterface.showMassage(type);
    }
    @Override
    public void onDebtDetailOperationFail(int type){
        debtListViewInterface.showMassage(type);
    }


}

package com.example.l.rBOOK.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.LoopCheck;
import com.example.l.rBOOK.model.dto.User;
import com.example.l.rBOOK.presenter.LoginPresenter;
import com.example.l.rBOOK.presenter.WelcomePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WelcomeFragment extends Fragment implements WelcomeViewInterface {

    private TextView name;
    private TextView id;
    private TextView total;
    private TextView rank;
    private TextView suggestion;
    private RecyclerView infoRecyclerView;
    private LoopAdapter loopAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    static GlobalData globalData;
    private WelcomePresenter welcomePresenter;
    private LoginPresenter loginPresenter;

    public static WelcomeFragment newInstance() {
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        Bundle args = new Bundle();
        welcomeFragment.setArguments(args);
        return welcomeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_fragment, container, false);
        globalData=(GlobalData) getActivity().getApplication();
        welcomePresenter=new WelcomePresenter(WelcomeFragment.this);
        loginPresenter=new LoginPresenter(WelcomeFragment.this);
        name=view.findViewById(R.id.welcome_name);
        id=view.findViewById(R.id.welcome_id);
        total=view.findViewById(R.id.welcome_total);
        rank=view.findViewById(R.id.welcome_rank);
        suggestion=view.findViewById(R.id.welcome_suggestion);
        bindUserInfo();

        infoRecyclerView = view.findViewById(R.id.welcome_recycler);
        infoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LoadList();

        swipeRefreshLayout = view.findViewById(R.id.welcome_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadList();
                updateUserData();
            }
        });

        return view;
    }

    @Override
    public void updateUser(User user){
        globalData.getPrimaryUser().setUserUsername(user.getUserUsername());
        globalData.getPrimaryUser().setUserAccount(user.getUserAccount());
        globalData.getPrimaryUser().setRankStatus(user.getRankStatus());
        globalData.getPrimaryUser().setTotal(user.getTotal());
        bindUserInfo();
    }


    @Override
    public void getLoopList(List<LoopCheck> list){
        loopAdapter = new LoopAdapter(list);
        infoRecyclerView.setAdapter(loopAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showMessage(){
        Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
    }
    public static Map<String,Object> bindUserHeader(){
        Map<String,Object> map=new HashMap<>();
        map.put("username",globalData.getPrimaryUser().getUserAccount());
        map.put("password",globalData.getPrimaryUser().getUserPassword());
        return map;
    }
    public void LoadList(){
        Map<String,Object> map=new HashMap<>();
        map.put("uh",bindUserHeader());
        welcomePresenter.getLoopList(getActivity(),map);
    }

    public void updateUserData(){
        loginPresenter.updateUserData(getActivity(),bindUserHeader());
    }

    public void bindUserInfo(){
        name.setText(globalData.getPrimaryUser().getUserUsername());
        id.setText(globalData.getPrimaryUser().getUserAccount());
        total.setText(convert(globalData.getPrimaryUser().getTotal()));
        if(globalData.getPrimaryUser().getTotal()>0){
            total.setTextColor(getResources().getColor(R.color.Green));
        }
        else if(globalData.getPrimaryUser().getTotal()<0){
            total.setTextColor(getResources().getColor(R.color.Red));
        }

        rank.setText(globalData.getPrimaryUser().getRankStatus()+"");
        int ranks=globalData.getPrimaryUser().getRankStatus();
        if(ranks>0&&ranks<20){
            suggestion.setText("Pay Attention To Timely Repayment!");
        }
        else if(ranks>=20&&ranks<40){
            suggestion.setText("Try To Reduce Debts");
        }
        else if(ranks>=40&&ranks<60){
            suggestion.setText("Financial Balanced");
        }
        else if(ranks>=60&&ranks<80){
            suggestion.setText("Try To Reduce Lending");
        }
        else if(ranks>=80&&ranks<100){
            suggestion.setText("Pay Attention To Fund Recovery!");
        }

    }

    private String convert(int num){
        String result=num+"";
        String number="";
        if(result.length()==1){
            result="00"+result;
        }
        else if(result.length()==2){
            result="0"+result;
        }
        String ptm="(\\d*)(\\d\\d)";
        Pattern p=Pattern.compile(ptm);
        Matcher m=p.matcher(result);
        if(m.find()){
            number=m.group(1)+"."+m.group(2);
        }
        return number;
    }
    private class LoopHolder extends RecyclerView.ViewHolder{
        private TextView me;
        private TextView left;
        private TextView right;
        private TextView scale;

        private LoopHolder(View itemView) {
            super(itemView);
            me=itemView.findViewById(R.id.welcome_item_me);
            left=itemView.findViewById(R.id.welcome_item_left_user);
            right=itemView.findViewById(R.id.welcome_item_right_user);
            scale=itemView.findViewById(R.id.welcome_item_scale);
        }
        private void bindInfo(LoopCheck loopCheck) {
            me.setText(globalData.getPrimaryUser().getUserUsername());
            left.setText(loopCheck.getStart());
            right.setText(loopCheck.getEnd());
            scale.setText(loopCheck.getScale()+"");
        }
    }

    private class LoopAdapter extends RecyclerView.Adapter<LoopHolder> {
        private List<LoopCheck> infoList;
        LoopAdapter(List<LoopCheck> infoList) {
            this.infoList = infoList;
        }


        @NonNull
        @Override
        public LoopHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.welcome_item, viewGroup, false);
            return new LoopHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LoopHolder loopHolder, int i) {
            LoopCheck info= infoList.get(i);
            loopHolder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }

}
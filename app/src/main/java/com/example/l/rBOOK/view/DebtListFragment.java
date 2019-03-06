package com.example.l.rBOOK.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.DebtTotal;
import com.example.l.rBOOK.presenter.DebtTotalTotalPresenter;
import com.github.clans.fab.FloatingActionButton;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebtListFragment extends Fragment implements DebtListViewInterface {
    @Nullable
    FloatingActionButton addDebtButton;
    private RecyclerView debtTotalRecyclerView;
    private DebtTotalAdapter debtTotalAdapter;
    private DebtTotalTotalPresenter debtTotalPresenter;
    private SwipeRefreshLayout debtTotalSwipeRefreshLayout;
    private List<DebtTotal> debtTotalList;
    private boolean click=false;
    private boolean clickDetail=false;
    private boolean ifVisible = false;
    private BottomSheetDialog debtTotalBottomSheetDialog;
    static GlobalData globalData;

    public static DebtListFragment newInstance() {
        DebtListFragment debtListFragment = new DebtListFragment();
        Bundle args = new Bundle();
        debtListFragment.setArguments(args);
        return debtListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.debt_list_fragment, container, false);

        globalData=(GlobalData) getActivity().getApplication();
        debtTotalPresenter = new DebtTotalTotalPresenter(DebtListFragment.this);
        debtTotalRecyclerView = view.findViewById(R.id.debt_total_recycler);
        debtTotalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        debtTotalAdapter = new DebtTotalAdapter(initDefaultInfoList());
        debtTotalRecyclerView.setAdapter(debtTotalAdapter);
        LoadList();

        debtTotalSwipeRefreshLayout = view.findViewById(R.id.info_list_slide_refresh);
        if (getActivity() != null) {
            addDebtButton = getActivity().findViewById(R.id.btn_debt_add);
        }
        debtTotalSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        debtTotalSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadList();
            }
        });

        addDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpBottomSheetDialog();
            }
        });
        debtTotalRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (addDebtButton != null) {
                        addDebtButton.hide(true);
                    }
                } else if (dy < 0) {
                    if (addDebtButton != null) {
                        addDebtButton.show(true);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void initList(List<DebtTotal> list)
    {
        debtTotalAdapter = new DebtTotalAdapter(list);
        debtTotalRecyclerView.setAdapter(debtTotalAdapter);
        debtTotalSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateList(List<DebtTotal> list){
        debtTotalList=list;
        debtTotalAdapter.notifyDataSetChanged();
        debtTotalSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(int type){
        debtTotalSwipeRefreshLayout.setRefreshing(false);
        if (type==0) {
            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_LONG).show();
        }
        else if(type==1){
            Toast.makeText(getActivity(), "Request Success", Toast.LENGTH_LONG).show();
            debtTotalBottomSheetDialog.cancel();
            LoadList();
        }
        else if(type==2){
            Toast.makeText(getActivity(), "Request Fail", Toast.LENGTH_LONG).show();
        }
    }

    private class DebtTotalHolder extends RecyclerView.ViewHolder{
        private DebtTotal debtTotal;
        private ConstraintLayout constraintLayout;
        private LinearLayout linearLayout;
        private TextView userTextView;
        private TextView priceTextView;
        private TextView leftArrow;
        private TextView rightArrow;
        private TextView date;
        private ImageView iconNew;
        private DebtTotalHolder(View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.debt_constraint_layout);
            userTextView=itemView.findViewById(R.id.debt_user);
            priceTextView=itemView.findViewById(R.id.debt_price);
            linearLayout=itemView.findViewById(R.id.debt_color_layout);
            leftArrow=itemView.findViewById(R.id.debt_arrow_left);
            rightArrow=itemView.findViewById(R.id.debt_arrow_right);
            date=itemView.findViewById(R.id.debt_update_date);
            iconNew=itemView.findViewById(R.id.icon_new);
        }
        private void bindInfo(DebtTotal debtTotal) {
            userTextView.setText(debtTotal.getName());
            priceTextView.setText(convert(debtTotal.getTotalNum()));
            priceTextView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
            priceTextView.getPaint().setFakeBoldText(true);
            if(debtTotal.getTotalNum()>0){
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.VISIBLE);
                priceTextView.setTextColor(getResources().getColor(R.color.Green));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.Green));
            }
            else if(debtTotal.getTotalNum()==0){
                leftArrow.setVisibility(View.INVISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
                priceTextView.setTextColor(getResources().getColor(R.color.grey));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.grey));
            }
            else if(debtTotal.getTotalNum()<0){
                leftArrow.setVisibility(View.VISIBLE);
                rightArrow.setVisibility(View.INVISIBLE);
                priceTextView.setTextColor(getResources().getColor(R.color.Red));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.Red));
            }
            if (debtTotal.isUnread()){
                iconNew.setVisibility(View.VISIBLE);
            }
            else iconNew.setVisibility(View.INVISIBLE);

            date.setText(debtTotal.getDate());
            
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DebtDetailPageActivity.class );
                    intent.putExtra("userName", debtTotal.getName());
                    intent.putExtra("userAccount", debtTotal.getAccount());
                    intent.putExtra("totalDebt",convert(debtTotal.getTotalNum()));
                    intent.putExtra("totalDebtInt",debtTotal.getTotalNum());
                    startActivity(intent);
                }
            });
        }
    }

    private class DebtTotalAdapter extends RecyclerView.Adapter<DebtTotalHolder> {
        private List<DebtTotal> infoList;
        DebtTotalAdapter(List<DebtTotal> infoList) {
            this.infoList = infoList;
        }

        @NonNull
        @Override
        public DebtTotalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.debt_item, viewGroup, false);
            return new DebtTotalHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DebtTotalHolder debtTotalHolder, int i) {
            DebtTotal info= infoList.get(i);
            debtTotalHolder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
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
            number=m.group(1)+"."+m.group(2)+" ";
        }
        return number;
    }

    public List<DebtTotal> initDefaultInfoList() {
        List<DebtTotal> defaultInfoList = new ArrayList<>();
        for(int i=0;i<1;i++){
            DebtTotal info = new DebtTotal();
            info.setDate(" ");
            info.setUnread(true);
            info.setTotalNum(000);
            info.setName("network error");
            info.setAccount("");
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }

    public void setUpBottomSheetDialog(){
        debtTotalBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.debt_add_sheet_layout, null);
        debtTotalBottomSheetDialog.setContentView(bottomSheetView);
        debtTotalBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        debtTotalBottomSheetDialog.setCancelable(false);
        debtTotalBottomSheetDialog.setCanceledOnTouchOutside(true);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_debt_add_cancel);
        MaterialButton send=bottomSheetView.findViewById(R.id.btn_debt_add_send);
        EditText editName=bottomSheetView.findViewById(R.id.debt_add_user_name);
        EditText editDesc=bottomSheetView.findViewById(R.id.debt_add_desc);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debtTotalBottomSheetDialog.cancel();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDesc.getText().toString().equals("")&&!editName.getText().toString().equals("")){
                    Map<String, Object> map = new HashMap<>();
                    Map<String,Object> map1=new HashMap<>();
                    map.put("username", globalData.getPrimaryUser().getUserAccount());
                    map.put("password", globalData.getPrimaryUser().getUserPassword());
                    map1.put("uh",map);
                    map1.put("counterName", editName.getText().toString());
                    map1.put("desc", editDesc.getText().toString());
                    debtTotalPresenter.sendAddPairRequest(getActivity(),map1);
                }
                else {
                    Toast.makeText(getActivity(), "Information Incomplete", Toast.LENGTH_LONG).show();
                }
            }
        });
        debtTotalBottomSheetDialog.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ifVisible = isVisibleToUser;
    }

    public void LoadList(){
        Map<String, Object> map = new HashMap<>();
        Map<String,Object>map1=new HashMap<>();
        map.put("username", globalData.getPrimaryUser().getUserAccount());
        map.put("password", globalData.getPrimaryUser().getUserPassword());
        map1.put("uh",map);
        debtTotalPresenter.loadInfoList(getActivity(),map1);
    }

}

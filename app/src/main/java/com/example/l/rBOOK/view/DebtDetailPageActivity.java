package com.example.l.rBOOK.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.DebtInfo;
import com.example.l.rBOOK.presenter.DebtDetailPresenter;
import com.example.l.rBOOK.presenter.DebtDetailPresenterInterface;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DebtDetailPageActivity extends AppCompatActivity implements DebtDetailPageInterface{

    private RecyclerView debtDetailRecyclerView;
    private DebtDetailAdapter debtDetailAdapter;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton addBtn;
    private FloatingActionButton deleteBtn;
    private FloatingActionButton combineBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialButton backBtn;
    private TextView userText;
    private TextView meText;
    private TextView totalDebt;
    private TextView leftArrow;
    private TextView rightArrow;
    private String userAccount="";
    private String userNickName="";
    private boolean multiSelect=false;
    private List<DebtInfo> debtInfoList=new ArrayList<>();

    private BottomSheetDialog debtDetailBottomSheetDialog;
    private BottomSheetDialog debtDetailAddBottomSheetDialog;
    private BottomSheetDialog debtDetailSelectBottomSheetDialog;

    private DebtDetailPresenterInterface debtDetailPresenter;

    public static GlobalData globalData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debt_datail);

        globalData=(GlobalData) getApplication();
        debtDetailPresenter=new DebtDetailPresenter(this);

        debtDetailRecyclerView=findViewById(R.id.debt_detail_recycler);
        debtDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        debtDetailRecyclerView.addItemDecoration(itemDecoration);

        floatingActionMenu=findViewById(R.id.debt_detail_floating_menu);
        addBtn=findViewById(R.id.btn_debt_detail_add);
        deleteBtn=findViewById(R.id.btn_debt_detail_delete);
        combineBtn=findViewById(R.id.btn_debt_detail_combine);
        setUpFloatingActionBtn();
        swipeRefreshLayout=findViewById(R.id.debt_detail_refresh);
        backBtn=findViewById(R.id.btn_debt_detail_back);
        userText=findViewById(R.id.debt_detail_user);
        meText=findViewById(R.id.debt_detail_me);
        totalDebt=findViewById(R.id.debt_detail_total_price);
        leftArrow=findViewById(R.id.debt_detail_arrow_left);
        rightArrow=findViewById(R.id.debt_detail_arrow_right);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        userText.setText(intent.getStringExtra("userName"));
        meText.setText(globalData.getPrimaryUser().getUserUsername());
        userNickName=intent.getStringExtra("userName");
        userAccount=intent.getStringExtra("userAccount");
        Map<String,Object> map=new HashMap<>();
        map.put("uh",bindUserHeader());
        map.put("counter",userAccount);
        debtDetailPresenter.loadDebtDetailList(this,map);

        debtDetailRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (floatingActionMenu != null) {
                        floatingActionMenu.hideMenu(true);
                    }
                } else if (dy < 0) {
                    if (floatingActionMenu != null) {
                        floatingActionMenu.showMenu(true);
                    }
                }
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String,Object> map=new HashMap<>();
                map.put("uh",bindUserHeader());
                map.put("counter",userAccount);
                debtDetailPresenter.loadDebtDetailList(DebtDetailPageActivity.this,map);
            }
        });


    }

    @Override
    public void getDebtDetailList(List<DebtInfo> list,int total){
        debtInfoList=list;
        debtDetailAdapter = new DebtDetailAdapter(list);
        debtDetailRecyclerView.setAdapter(debtDetailAdapter);
        totalDebt.setText(convert(total));
        totalDebt.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        totalDebt.getPaint().setFakeBoldText(true);
        if(total>0){
            leftArrow.setVisibility(View.INVISIBLE);
            rightArrow.setVisibility(View.VISIBLE);
            totalDebt.setTextColor(getResources().getColor(R.color.Green));
        }
        else if(total<0){
            leftArrow.setVisibility(View.VISIBLE);
            rightArrow.setVisibility(View.INVISIBLE);
            totalDebt.setTextColor(getResources().getColor(R.color.Red));
        }
        else if(total==0){
            leftArrow.setVisibility(View.INVISIBLE);
            rightArrow.setVisibility(View.INVISIBLE);
            totalDebt.setTextColor(getResources().getColor(R.color.grey));
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showMassage(int type){
        swipeRefreshLayout.setRefreshing(false);
        switch (type){
            case 0: Toast.makeText(this, "Request Fail", Toast.LENGTH_LONG).show();break;
            case 1:
                Toast.makeText(this, "Add Debt Success", Toast.LENGTH_LONG).show();
                debtDetailAddBottomSheetDialog.cancel();
                LoadList();
                break;
            case 2: Toast.makeText(this, "Add Debt Fail", Toast.LENGTH_LONG).show();break;
            case 3:
                Toast.makeText(this, "Delete Debt Success", Toast.LENGTH_LONG).show();
                debtDetailSelectBottomSheetDialog.cancel();
                LoadList();
                break;
            case 4: Toast.makeText(this, "Delete Debt Fail", Toast.LENGTH_LONG).show();break;
            case 5:
                Toast.makeText(this, "Combine Debt Success", Toast.LENGTH_LONG).show();
                debtDetailSelectBottomSheetDialog.cancel();
                LoadList();
                break;
            case 6: Toast.makeText(this, "Combine Debt Fail", Toast.LENGTH_LONG).show();break;
            case 7:
                Toast.makeText(this, "Accept Success", Toast.LENGTH_LONG).show();
                debtDetailBottomSheetDialog.cancel();
                LoadList();
                break;
            case 8: Toast.makeText(this, "Accept Fail", Toast.LENGTH_LONG).show();break;
            case 9:
                Toast.makeText(this, "Refuse Success", Toast.LENGTH_LONG).show();
                debtDetailBottomSheetDialog.cancel();
                LoadList();
                break;
            case 10: Toast.makeText(this, "Refuse Fail", Toast.LENGTH_LONG).show();break;
            case 11: Toast.makeText(this, "Load List Fail", Toast.LENGTH_LONG).show();break;
        }

    }


    private class DebtDetailHolder extends RecyclerView.ViewHolder{
        private DebtInfo debtinfo;
        private RelativeLayout constraintLayout;
        private LinearLayout linearLayout;
        private TextView priceTextView;
        private TextView arrow;
        private TextView descText;
        private TextView date;
        private ImageView iconNew;
        private TextView statusText;
        private CheckBox checkBox;
        private DebtDetailHolder(View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.debt_detail_relative_layout);
            descText=itemView.findViewById(R.id.debt_detail_desc_text);
            priceTextView=itemView.findViewById(R.id.debt_detail_price);
            linearLayout=itemView.findViewById(R.id.debt_detail_color_layout);
            arrow=itemView.findViewById(R.id.debt_detail_arrow);
            date=itemView.findViewById(R.id.debt_detail_date);
            iconNew=itemView.findViewById(R.id.debt_detail_new_image);
            statusText=itemView.findViewById(R.id.debt_detail_status_text);
            checkBox=itemView.findViewById(R.id.debt_detail_select_box);
        }
        private void bindInfo(DebtInfo debtInfo,int position,ArrayList<Boolean> booleans) {
            priceTextView.setText(convert(debtInfo.getNum()));
            priceTextView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
            priceTextView.getPaint().setFakeBoldText(true);
            date.setText(debtInfo.getDate());
            descText.setText(debtInfo.getDesc());
            checkBox.setVisibility(View.GONE);
            if(multiSelect&&debtInfo.getStatus()==0){
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        booleans.set(position,isChecked);
                    }
                });
            }

            if(debtInfo.getNum()==0){
                arrow.setText("▲");
                priceTextView.setTextColor(getResources().getColor(R.color.grey));
                arrow.setTextColor(getResources().getColor(R.color.grey));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.grey));
            }
            else if(debtInfo.getStart().equals(globalData.getPrimaryUser().getUserAccount())){
                arrow.setText("▶");
                priceTextView.setTextColor(getResources().getColor(R.color.Green));
                arrow.setTextColor(getResources().getColor(R.color.Green));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.Green));
            }
            else {
                arrow.setText("◀");
                priceTextView.setTextColor(getResources().getColor(R.color.Red));
                arrow.setTextColor(getResources().getColor(R.color.Red));
                linearLayout.setBackgroundColor(getResources().getColor(R.color.Red));
            }
            if (debtInfo.getStatus()==0){
                iconNew.setVisibility(View.INVISIBLE);
                statusText.setText("");
                float alpha=1f;
                descText.setAlpha(alpha);
                constraintLayout.setAlpha(alpha);
                priceTextView.setAlpha(alpha);
                linearLayout.setAlpha(alpha);
                arrow.setAlpha(alpha);
                date.setAlpha(alpha);
            }
            else if(debtInfo.getStatus()==1){
                iconNew.setVisibility(View.VISIBLE);
                statusText.setText("Add");
                float alpha=1f;
                descText.setAlpha(alpha);
                constraintLayout.setAlpha(alpha);
                priceTextView.setAlpha(alpha);
                linearLayout.setAlpha(alpha);
                arrow.setAlpha(alpha);
                date.setAlpha(alpha);
            }
            else if(debtInfo.getStatus()==2){
                iconNew.setVisibility(View.VISIBLE);
                statusText.setText("Delete");
                float alpha=1f;
                descText.setAlpha(alpha);
                constraintLayout.setAlpha(alpha);
                priceTextView.setAlpha(alpha);
                linearLayout.setAlpha(alpha);
                arrow.setAlpha(alpha);
                date.setAlpha(alpha);
            }
            else if(debtInfo.getStatus()==3){
                float alpha=0.7f;
                iconNew.setVisibility(View.INVISIBLE);
                statusText.setText("");
                descText.setAlpha(alpha);
                constraintLayout.setAlpha(alpha);
                priceTextView.setAlpha(alpha);
                linearLayout.setAlpha(alpha);
                arrow.setAlpha(alpha);
                date.setAlpha(alpha);
            }
            else if(debtInfo.getStatus()==4){
                iconNew.setVisibility(View.VISIBLE);
                statusText.setText("Combine");
                float alpha=1f;
                descText.setAlpha(alpha);
                constraintLayout.setAlpha(alpha);
                priceTextView.setAlpha(alpha);
                linearLayout.setAlpha(alpha);
                arrow.setAlpha(alpha);
                date.setAlpha(alpha);
            }
            if(!multiSelect){
                constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        debtDetailBottomSheetDialog = new BottomSheetDialog(DebtDetailPageActivity.this);
                        View bottomSheetView = LayoutInflater.from(DebtDetailPageActivity.this).inflate(R.layout.debt_detail_sheet_layout, null);
                        debtDetailBottomSheetDialog.setContentView(bottomSheetView);
                        debtDetailBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(DebtDetailPageActivity.this, android.R.color.transparent));
                        debtDetailBottomSheetDialog.setCancelable(false);
                        debtDetailBottomSheetDialog.setCanceledOnTouchOutside(true);
                        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_debt_detail_sheet_cancel);
                        TextView meText=bottomSheetView.findViewById(R.id.debt_detail_sheet_me);
                        meText.setText(globalData.getPrimaryUser().getUserUsername());
                        TextView userText=bottomSheetView.findViewById(R.id.debt_detail_sheet_user);
                        TextView left=bottomSheetView.findViewById(R.id.debt_detail_sheet_arrow_left);
                        TextView right=bottomSheetView.findViewById(R.id.debt_detail_sheet_arrow_right);
                        TextView number=bottomSheetView.findViewById(R.id.debt_detail_sheet_price);
                        TextView operation=bottomSheetView.findViewById(R.id.debt_detail_sheet_operation);
                        TextView otext=bottomSheetView.findViewById(R.id.debt_detail_sheet_otext);
                        TextView desc=bottomSheetView.findViewById(R.id.debt_detail_sheet_desc);
                        MaterialButton accept=bottomSheetView.findViewById(R.id.btn_debt_detail_accept);
                        MaterialButton refuse=bottomSheetView.findViewById(R.id.btn_debt_detail_refuse);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                debtDetailBottomSheetDialog.cancel();
                            }
                        });
                        userText.setText(userNickName);
                        number.setText(convert(debtInfo.getNum()));
                        number.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
                        number.getPaint().setFakeBoldText(true);

                        if(debtInfo.getNum()==0){
                            left.setVisibility(View.INVISIBLE);
                            right.setVisibility(View.INVISIBLE);
                            number.setTextColor(getResources().getColor(R.color.grey));
                        }
                        else if(debtInfo.getStart().equals(globalData.getPrimaryUser().getUserAccount())){
                            left.setVisibility(View.INVISIBLE);
                            right.setVisibility(View.VISIBLE);
                            number.setTextColor(getResources().getColor(R.color.Green));
                        }
                        else {
                            left.setVisibility(View.VISIBLE);
                            right.setVisibility(View.INVISIBLE);
                            number.setTextColor(getResources().getColor(R.color.Red));
                        }
                        desc.setText(debtInfo.getDesc());
                        if(debtInfo.getStatus()==0||debtInfo.getStatus()==3){
                            otext.setVisibility(View.GONE);
                            operation.setVisibility(View.GONE);
                            accept.setVisibility(View.GONE);
                            refuse.setVisibility(View.GONE);
                        }
                        else if(debtInfo.getStatus()==1){
                            operation.setText("Add");
                            if(globalData.getPrimaryUser().getUserAccount().equals(debtInfo.getStart())==debtInfo.isProposal()){
                                accept.setVisibility(View.GONE);
                                refuse.setVisibility(View.GONE);
                            }
                        }
                        else if(debtInfo.getStatus()==2){
                            operation.setText("Delete");
                            if(globalData.getPrimaryUser().getUserAccount().equals(debtInfo.getStart())==debtInfo.isProposal()){
                                accept.setVisibility(View.GONE);
                                refuse.setVisibility(View.GONE);
                            }
                        }
                        else if(debtInfo.getStatus()==4){
                            operation.setText("Combine");
                            if(globalData.getPrimaryUser().getUserAccount().equals(debtInfo.getStart())==debtInfo.isProposal()){
                                accept.setVisibility(View.GONE);
                                refuse.setVisibility(View.GONE);
                            }
                        }
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> map=new HashMap<>();
                                map.put("uh",bindUserHeader());
                                map.put("id",debtInfo.getId());
                                map.put("counter",userAccount);
                                map.put("status",debtInfo.getStatus());
                                debtDetailPresenter.acceptConfirm(DebtDetailPageActivity.this,map);
                                LoadList();
                            }
                        });
                        refuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String,Object> map=new HashMap<>();
                                map.put("uh",bindUserHeader());
                                map.put("id",debtInfo.getId());
                                map.put("counter",userAccount);
                                map.put("status",debtInfo.getStatus());
                                debtDetailPresenter.refuseConfirm(DebtDetailPageActivity.this,map);
                                LoadList();
                            }
                        });
                        debtDetailBottomSheetDialog.show();

                    }
                });
            }

        }
    }
    private class DebtDetailAdapter extends RecyclerView.Adapter<DebtDetailHolder> {
        private List<DebtInfo> infoList;
        private ArrayList<Boolean> booleanList=new ArrayList<>();
        DebtDetailAdapter(List<DebtInfo> infoList) {
            this.infoList = infoList;
            for(int i=0;i<infoList.size();i++){
                booleanList.add(false);
            }
        }
        public List<DebtInfo> getResultList(){
            List<DebtInfo> resultList=new ArrayList<>();
            if(multiSelect){
                for(int i=0;i<booleanList.size();i++){
                    if(booleanList.get(i)){
                        resultList.add(infoList.get(i));
                    }
                }
                return resultList;
            }
            else return resultList;
        }
        @NonNull
        @Override
        public DebtDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(DebtDetailPageActivity.this);
            View view = layoutInflater.inflate(R.layout.debt_detail_item, viewGroup, false);
            return new DebtDetailHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DebtDetailHolder debtDetailHolder, int i) {
            DebtInfo info= infoList.get(i);
            if(multiSelect){
                if(info.getStatus()==0){
                    debtDetailHolder.bindInfo(info,i,booleanList);
                }
            }
            else debtDetailHolder.bindInfo(info,i,booleanList);

        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }
    private void setUpFloatingActionBtn() {
        addBtn.setOnClickListener(v -> {
            setUpAddBottomSheetDialog();
        });

        deleteBtn.setOnClickListener(v -> {
            setUpDeleteBottomSheetDialog();

        });

        combineBtn.setOnClickListener(v -> {
            setUpCombineBottomSheetDialog();
        });


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

    public List<DebtInfo> getNormalList(){
        List<DebtInfo> list=new ArrayList<>();
        for(int i=0;i<debtInfoList.size();i++) {
            if (debtInfoList.get(i).getStatus() == 0) {
                list.add(debtInfoList.get(i));
            }
        }
        return list;
    }
    @Override
    public void onBackPressed() {
        if (multiSelect) {
            multiSelect=false;
            super.onBackPressed();
        }
        else super.onBackPressed();
    }

    public void setUpCombineBottomSheetDialog(){
        floatingActionMenu.close(true);
        multiSelect=true;
        debtDetailSelectBottomSheetDialog = new BottomSheetDialog(DebtDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(DebtDetailPageActivity.this).inflate(R.layout.debt_detail_delete_combine_sheet_layout, null);
        debtDetailSelectBottomSheetDialog.setContentView(bottomSheetView);
        debtDetailSelectBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(DebtDetailPageActivity.this, android.R.color.transparent));
        debtDetailSelectBottomSheetDialog.setCancelable(false);
        debtDetailSelectBottomSheetDialog.setCanceledOnTouchOutside(false);
        TextView title=bottomSheetView.findViewById(R.id.sheet_title_multi);
        EditText desc=bottomSheetView.findViewById(R.id.edit_combine_desc);
        title.setText("Combine Debt");
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_debt_detail_multi_cancel);
        RecyclerView selectRecyclerView=bottomSheetView.findViewById(R.id.debt_detail_multi_recycler);
        MaterialButton send=bottomSheetView.findViewById(R.id.btn_debt_detail_multi_send);
        send.setText("COMBINE");
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        debtDetailAdapter = new DebtDetailAdapter(getNormalList());
        selectRecyclerView.setAdapter(debtDetailAdapter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelect=false;
                debtDetailSelectBottomSheetDialog.cancel();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(debtDetailAdapter.getResultList().size()>1){
                    if(!desc.getText().toString().equals("")){
                        ArrayList<String> list=new ArrayList<>();
                        List<DebtInfo> rlist=debtDetailAdapter.getResultList();
                        for(int i=0;i<rlist.size();i++){
                            list.add(rlist.get(i).getId());
                        }
                        Map<String,Object> map=new HashMap<>();
                        map.put("uh",bindUserHeader());
                        map.put("counter",userAccount);
                        map.put("idList",list);
                        map.put("desc",desc.getText().toString());
                        debtDetailPresenter.combineDebt(DebtDetailPageActivity.this,map);
                        multiSelect=false;
                        debtDetailSelectBottomSheetDialog.cancel();
                        LoadList();
                    }
                    else {
                        Toast.makeText(DebtDetailPageActivity.this, "Incomplete Information", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(DebtDetailPageActivity.this, "Please Select At Least 2 Debts", Toast.LENGTH_LONG).show();
                }
            }
        });
        debtDetailSelectBottomSheetDialog.show();
    }
    public void setUpAddBottomSheetDialog(){
        floatingActionMenu.close(true);
        debtDetailAddBottomSheetDialog = new BottomSheetDialog(DebtDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(DebtDetailPageActivity.this).inflate(R.layout.debt_detail_add_sheet_layout, null);
        debtDetailAddBottomSheetDialog.setContentView(bottomSheetView);
        debtDetailAddBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(DebtDetailPageActivity.this, android.R.color.transparent));
        debtDetailAddBottomSheetDialog.setCancelable(false);
        debtDetailAddBottomSheetDialog.setCanceledOnTouchOutside(true);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_debt_detail_add_cancel);
        EditText editNumber=bottomSheetView.findViewById(R.id.debt_detail_add_edit_amount);
        EditText editDesc=bottomSheetView.findViewById(R.id.debt_detail_add_edit_desc);
        CheckBox checkBoxL=bottomSheetView.findViewById(R.id.debt_detail_add_check_lend);
        CheckBox checkBoxB=bottomSheetView.findViewById(R.id.debt_detail_add_check_borrow);
        MaterialButton send=bottomSheetView.findViewById(R.id.btn_debt_detail_add_send);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debtDetailAddBottomSheetDialog.cancel();
            }
        });
        checkBoxL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxL.isChecked()) {
                    checkBoxB.setChecked(false);
                }
                else checkBoxB.setChecked(true);
            }
        });
        checkBoxB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxB.isChecked()) {
                    checkBoxL.setChecked(false);
                }
                else checkBoxL.setChecked(true);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDesc.getText().toString().equals("")&&!editNumber.getText().toString().equals("")&&(checkBoxB.isChecked()||checkBoxL.isChecked())){

                    boolean direct=true;
                    if(checkBoxL.isChecked()){
                        direct=true;
                    }
                    else if(checkBoxB.isChecked()){
                        direct=false;
                    }
                    String num=editNumber.getText().toString();
                    boolean flag=true;
                    float sourceF=-1.0f;
                    try {
                        sourceF = Float.parseFloat(num);
                    }
                    catch (Exception e){
                        flag=false;
                    }
                    if(flag){
                        sourceF=sourceF*100;
                        int number = Math.round(sourceF);
                        Map<String,Object> map=new HashMap<>();
                        map.put("uh",bindUserHeader());
                        map.put("counterName",userAccount);
                        map.put("num",number);
                        map.put("desc",editDesc.getText().toString());
                        map.put("direct",direct);
                        debtDetailPresenter.addDebt(DebtDetailPageActivity.this,map);
                        multiSelect=false;
                        LoadList();
                    }
                    else {
                        Toast.makeText(DebtDetailPageActivity.this, "Error Number", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(DebtDetailPageActivity.this, "Information Incomplete", Toast.LENGTH_LONG).show();
                }
            }
        });
        debtDetailAddBottomSheetDialog.show();
    }
    public void setUpDeleteBottomSheetDialog(){
        floatingActionMenu.close(true);
        multiSelect=true;
        debtDetailSelectBottomSheetDialog = new BottomSheetDialog(DebtDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(DebtDetailPageActivity.this).inflate(R.layout.debt_detail_delete_combine_sheet_layout, null);
        debtDetailSelectBottomSheetDialog.setContentView(bottomSheetView);
        debtDetailSelectBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(DebtDetailPageActivity.this, android.R.color.transparent));
        debtDetailSelectBottomSheetDialog.setCancelable(false);
        debtDetailSelectBottomSheetDialog.setCanceledOnTouchOutside(false);
        TextView title=bottomSheetView.findViewById(R.id.sheet_title_multi);
        EditText desc=bottomSheetView.findViewById(R.id.edit_combine_desc);
        desc.setVisibility(View.GONE);
        title.setText("Delete Debt");
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_debt_detail_multi_cancel);
        RecyclerView selectRecyclerView=bottomSheetView.findViewById(R.id.debt_detail_multi_recycler);
        MaterialButton send=bottomSheetView.findViewById(R.id.btn_debt_detail_multi_send);
        send.setText("DELETE");
        selectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        debtDetailAdapter = new DebtDetailAdapter(getNormalList());
        selectRecyclerView.setAdapter(debtDetailAdapter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiSelect=false;
                debtDetailSelectBottomSheetDialog.cancel();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(debtDetailAdapter.getResultList().size()!=0){
                    ArrayList<String> list=new ArrayList<>();
                    List<DebtInfo> rlist=debtDetailAdapter.getResultList();
                    for(int i=0;i<rlist.size();i++){
                        list.add(rlist.get(i).getId());
                    }
                    Map<String,Object> map=new HashMap<>();
                    map.put("uh",bindUserHeader());
                    map.put("counter",userAccount);
                    map.put("idList",list);
                    map.put("desc","");
                    debtDetailPresenter.deleteDebt(DebtDetailPageActivity.this,map);
                    multiSelect=false;
                    debtDetailSelectBottomSheetDialog.cancel();
                    LoadList();
                }
                else {
                    Toast.makeText(DebtDetailPageActivity.this, "Please Select", Toast.LENGTH_LONG).show();
                }
            }
        });
        debtDetailSelectBottomSheetDialog.show();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public static Map<String,Object> bindUserHeader(){
        Map<String,Object> map=new HashMap<>();
        map.put("username",globalData.getPrimaryUser().getUserAccount());
        map.put("password",globalData.getPrimaryUser().getUserPassword());
        return map;
    }
    public void LoadList(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("counter",userAccount);
        debtDetailPresenter.loadDebtDetailList(DebtDetailPageActivity.this,map1);
    }
}

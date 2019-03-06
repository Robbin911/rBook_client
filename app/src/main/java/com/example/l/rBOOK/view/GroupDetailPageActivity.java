package com.example.l.rBOOK.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.Group;
import com.example.l.rBOOK.model.dto.GroupDetail;
import com.example.l.rBOOK.model.dto.GroupResInfo;
import com.example.l.rBOOK.model.dto.User;
import com.example.l.rBOOK.presenter.GroupDetailPresenter;
import com.example.l.rBOOK.presenter.GroupDetailPresenterInterface;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupDetailPageActivity extends AppCompatActivity implements GroupDetailPageInterface{
    private RecyclerView groupRecyclerView;
    private RecyclerView itemRecycler;
    private RecyclerView addRecycler;
    private RecyclerView memberRecycler;
    private RecyclerView checkRecycler;
    private GroupDetailAdapter groupDetailAdapter;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton addBtn;
    private FloatingActionButton memberBtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialButton backBtn;
    private MaterialButton confirmBtn;
    private MaterialButton checkBtn;
    private TextView groupName;
    private TextView groupStauts;
    private TextView myStatus;
    private TextView myStatusText;
    private TextView uuidText;
    private BottomSheetDialog groupDetailConfirmSheet;
    private BottomSheetDialog groupDetailCheckSheet;
    private BottomSheetDialog groupDetailMemberSheet;
    private BottomSheetDialog groupDetailAddSheet;
    private BottomSheetDialog groupDetailItemSheet;

    private int status;
    private boolean myConfirm;
    private String groupUuid="";

    private boolean multiSelect;

    private List<GroupDetail> groupDetailList=new ArrayList<>();

    private MemberAdapter memberAdapter;

    private GroupDetailPresenterInterface groupDetailPresenter;

    public static GlobalData globalData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);

        globalData=(GlobalData) getApplication();

        groupDetailPresenter=new GroupDetailPresenter(this);
        groupRecyclerView=findViewById(R.id.group_detail_recycler);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        floatingActionMenu=findViewById(R.id.group_detail_floating_menu);
        addBtn=findViewById(R.id.btn_group_detail_add);
        memberBtn=findViewById(R.id.btn_group_detail_member);
        swipeRefreshLayout=findViewById(R.id.group_detail_refresh);
        backBtn=findViewById(R.id.btn_group_detail_back);
        confirmBtn=findViewById(R.id.btn_group_detail_confirm);
        checkBtn=findViewById(R.id.btn_group_detail_check);
        groupName=findViewById(R.id.group_detail_name);
        groupStauts=findViewById(R.id.group_detail_status);
        myStatus=findViewById(R.id.group_detail_my_status);
        uuidText=findViewById(R.id.group_detail_id);
        myStatusText=findViewById(R.id.text_my_con);
        confirmBtn.setVisibility(View.GONE);
        checkBtn.setVisibility(View.GONE);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        groupName.setText(intent.getStringExtra("groupName"));
        groupUuid=intent.getStringExtra("groupUUID");
        status=intent.getIntExtra("groupStatus",0);
        myConfirm=intent.getBooleanExtra("groupMyConfirm",false);
        uuidText.setText(groupUuid);
        uuidText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(groupUuid);
                Toast.makeText(GroupDetailPageActivity.this, "Group ID Copied To Clipboard", Toast.LENGTH_LONG).show();
            }
        });
        setUpFloatingActionBtn();
        setUpGroupInfo(status,myConfirm);
        UpdateGroupInfo();
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpCheckBottomSheetDialog();
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpConfirmBottomSheetDialog();
            }
        });

        groupDetailList=initDefaultList();
        groupDetailAdapter=new GroupDetailAdapter(groupDetailList);
        groupRecyclerView.setAdapter(groupDetailAdapter);

        LoadDebtList();
        groupRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                LoadDebtList();
                UpdateGroupInfo();
            }
        });


    }

    @Override
    public void setGroupInfo(Group group){
        setUpGroupInfo(group.getStatus(),group.isMyConfirm());
    }
    @Override
    public void setDebtList(List<GroupDetail> list){
        groupDetailAdapter=new GroupDetailAdapter(list);
        groupRecyclerView.setAdapter(groupDetailAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setDebtMemberList(List<User> list){
        if(groupDetailItemSheet!=null){
            memberAdapter=new MemberAdapter(list,false);
            itemRecycler.setAdapter(memberAdapter);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void setMemberList(List<User> list){
        if(groupDetailMemberSheet!=null){
            memberAdapter=new MemberAdapter(list,false);
            memberRecycler.setAdapter(memberAdapter);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void setAddList(List<User> list){
        if(groupDetailAddSheet!=null){
            memberAdapter=new MemberAdapter(list,true);
            addRecycler.setLayoutManager(new LinearLayoutManager(this));
            addRecycler.setAdapter(memberAdapter);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void setCheckList(List<GroupResInfo> list){
        if(groupDetailCheckSheet!=null){
            CheckAdapter checkAdapter=new CheckAdapter(list);
            checkRecycler.setLayoutManager(new LinearLayoutManager(this));
            checkRecycler.setAdapter(checkAdapter);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showMessage(int type){
        swipeRefreshLayout.setRefreshing(false);
        switch (type){
            case 0:
                Toast.makeText(this, "Request Fail", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(this, "Add Success", Toast.LENGTH_LONG).show();
                if(groupDetailAddSheet!=null){
                    groupDetailAddSheet.cancel();
                }
                LoadDebtList();
                UpdateGroupInfo();
                break;
            case 2:
                Toast.makeText(this, "Delete Success", Toast.LENGTH_LONG).show();
                if(groupDetailItemSheet!=null){
                    LoadDebtList();
                    UpdateGroupInfo();
                    groupDetailItemSheet.cancel();
                }
                break;
            case 3:
                Toast.makeText(this, "Confirm Success", Toast.LENGTH_LONG).show();
                if(groupDetailConfirmSheet!=null){
                    LoadDebtList();
                    UpdateGroupInfo();
                    groupDetailConfirmSheet.cancel();
                }
                break;
            case 4:
                Toast.makeText(this, "Check Success", Toast.LENGTH_LONG).show();
                LoadCheckList();
                UpdateGroupInfo();
                break;

        }
    }

    private class GroupDetailHolder extends RecyclerView.ViewHolder{
        private GroupDetail groupDetailInfo;
        private RelativeLayout constraintLayout;
        private TextView descText;
        private TextView amountText;
        private TextView date;
        private TextView proposalName;
        private GroupDetailHolder(View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.group_detail_relative_layout);
            descText=itemView.findViewById(R.id.group_detail_desc_text);
            amountText=itemView.findViewById(R.id.group_detail_amount);
            proposalName=itemView.findViewById(R.id.group_detail_username);
            date=itemView.findViewById(R.id.group_detail_date);
        }
        private void bindInfo(GroupDetail groupDetail) {
            amountText.setText(convert(groupDetail.getNum()));
            proposalName.setText(groupDetail.getProposeNickName());
            date.setText(groupDetail.getDate());
            descText.setText(groupDetail.getDesc());
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUpItemBottomSheetDialog(groupDetail.getUuid(),groupDetail.getProposeNickName(),groupDetail.getDesc(),groupDetail.getDate(),amountText.getText().toString(),groupDetail.isPropose());
                }
            });

        }
    }
    private class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailHolder> {
        private List<GroupDetail> infoList;
        GroupDetailAdapter(List<GroupDetail> infoList) {
            this.infoList = infoList;
        }
        @NonNull
        @Override
        public GroupDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(GroupDetailPageActivity.this);
            View view = layoutInflater.inflate(R.layout.group_detail_item, viewGroup, false);
            return new GroupDetailHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GroupDetailHolder groupHolder, int i) {
            GroupDetail info= infoList.get(i);
            groupHolder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }

    private void setUpFloatingActionBtn() {
        if(status==0&&!myConfirm){
            addBtn.setOnClickListener(v -> {
                setUpAddBottomSheetDialog();
            });
        }
        else addBtn.setVisibility(View.GONE);

        memberBtn.setOnClickListener(v -> {
            setUpMemberBottomSheetDialog();
        });


    }

    public void setUpConfirmBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupDetailConfirmSheet = new BottomSheetDialog(GroupDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(GroupDetailPageActivity.this).inflate(R.layout.group_detail_confirm_sheet, null);
        groupDetailConfirmSheet.setContentView(bottomSheetView);
        groupDetailConfirmSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(GroupDetailPageActivity.this, android.R.color.transparent));
        groupDetailConfirmSheet.setCancelable(true);
        groupDetailConfirmSheet.setCanceledOnTouchOutside(true);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_detail_confirm_sheet_cancel);
        MaterialButton accept=bottomSheetView.findViewById(R.id.btn_debt_detail_accept_confirm);
        MaterialButton refuse=bottomSheetView.findViewById(R.id.btn_group_detail_refuse_confirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailConfirmSheet.cancel();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmGroupRequest();
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailConfirmSheet.cancel();
            }
        });
        groupDetailConfirmSheet.show();
    }

    public void setUpItemBottomSheetDialog(String uuid,String name,String desc,String date,String num,boolean propose){
        floatingActionMenu.close(true);
        groupDetailItemSheet = new BottomSheetDialog(GroupDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(GroupDetailPageActivity.this).inflate(R.layout.group_detail_item_sheet, null);
        groupDetailItemSheet.setContentView(bottomSheetView);
        groupDetailItemSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(GroupDetailPageActivity.this, android.R.color.transparent));
        groupDetailItemSheet.setCancelable(true);
        groupDetailItemSheet.setCanceledOnTouchOutside(true);
        TextView descText=bottomSheetView.findViewById(R.id.group_detail_item_sheet_desc_text);
        TextView dateText=bottomSheetView.findViewById(R.id.group_detail_item_sheet_date);
        TextView amountText=bottomSheetView.findViewById(R.id.group_detail_item_sheet_amount);
        TextView nameText=bottomSheetView.findViewById(R.id.group_detail_item_sheet_username);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_detail_item_sheet_cancel);
        MaterialButton delete=bottomSheetView.findViewById(R.id.btn_group_detail_item_sheet_delete);
        itemRecycler=bottomSheetView.findViewById(R.id.group_detail_item_sheet_recycler);

        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",uuid);
        groupDetailPresenter.getGroupDebtMemberList(GroupDetailPageActivity.this,map1);

        MemberAdapter memberAdapter=new MemberAdapter(userList(),false);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setAdapter(memberAdapter);


        descText.setText(desc);
        dateText.setText(date);
        amountText.setText(num);
        nameText.setText(name);
        delete.setVisibility(View.GONE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailItemSheet.cancel();
                groupDetailItemSheet=null;
            }
        });
        if(propose&&status==0){
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> list=new ArrayList<>();
                    list.add(uuid);
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("uh",bindUserHeader());
                    map1.put("list",list);
                    map1.put("uuid",groupUuid);
                    groupDetailPresenter.deleteGroupDebt(GroupDetailPageActivity.this,map1);
                }
            });
        }
        groupDetailItemSheet.show();
    }

    public void setUpAddBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupDetailAddSheet = new BottomSheetDialog(GroupDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(GroupDetailPageActivity.this).inflate(R.layout.group_detail_add_sheet, null);
        groupDetailAddSheet.setContentView(bottomSheetView);
        groupDetailAddSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(GroupDetailPageActivity.this, android.R.color.transparent));
        groupDetailAddSheet.setCancelable(true);
        groupDetailAddSheet.setCanceledOnTouchOutside(true);
        EditText descText=bottomSheetView.findViewById(R.id.group_detail_add_edit_desc);
        EditText amountText=bottomSheetView.findViewById(R.id.group_detail_add_edit_amount);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_detail_add_cancel);
        MaterialButton submit=bottomSheetView.findViewById(R.id.btn_group_detail_add_sheet_submit);
        addRecycler=bottomSheetView.findViewById(R.id.group_detail_add_sheet_recycler);

        LoadAddList();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailAddSheet.cancel();
                groupDetailAddSheet=null;
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!descText.getText().toString().equals("")&&!amountText.getText().toString().equals("")){

                    String num=amountText.getText().toString();
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
                         List<String> list=memberAdapter.getResultList();
                        if(list.size()>0){
                            Map<String,Object> map1=new HashMap<>();
                            map1.put("uh",bindUserHeader());
                            map1.put("guid",groupUuid);
                            map1.put("desc",descText.getText().toString());
                            map1.put("num",number);
                            map1.put("targetList",list);
                            groupDetailPresenter.addGroupDebt(GroupDetailPageActivity.this,map1);
                            }
                        else {
                            Toast.makeText(GroupDetailPageActivity.this, "Please Select At Least 1 Member", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(GroupDetailPageActivity.this, "Error Number", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(GroupDetailPageActivity.this, "Incomplete Information", Toast.LENGTH_LONG).show();
                }
            }
        });
        groupDetailAddSheet.show();
    }
    public void setUpMemberBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupDetailMemberSheet = new BottomSheetDialog(GroupDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(GroupDetailPageActivity.this).inflate(R.layout.group_detail_member_sheet, null);
        groupDetailMemberSheet.setContentView(bottomSheetView);
        groupDetailMemberSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(GroupDetailPageActivity.this, android.R.color.transparent));
        groupDetailMemberSheet.setCancelable(true);
        groupDetailMemberSheet.setCanceledOnTouchOutside(true);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_detail_member_sheet_cancel);
        memberRecycler=bottomSheetView.findViewById(R.id.group_detail_member_sheet_recycler);

        LoadMemberList();

        MemberAdapter memberAdapter=new MemberAdapter(userList(),false);
        memberRecycler.setLayoutManager(new LinearLayoutManager(this));
        memberRecycler.setAdapter(memberAdapter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailMemberSheet.cancel();
                groupDetailMemberSheet=null;
            }
        });

        groupDetailMemberSheet.show();
    }
    public void setUpCheckBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupDetailCheckSheet = new BottomSheetDialog(GroupDetailPageActivity.this);
        View bottomSheetView = LayoutInflater.from(GroupDetailPageActivity.this).inflate(R.layout.group_check_sheet, null);
        groupDetailCheckSheet.setContentView(bottomSheetView);
        groupDetailCheckSheet.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(GroupDetailPageActivity.this, android.R.color.transparent));
        groupDetailCheckSheet.setCancelable(true);
        groupDetailCheckSheet.setCanceledOnTouchOutside(true);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_check_cancel);
        checkRecycler=bottomSheetView.findViewById(R.id.group_check_recycler);

        LoadCheckList();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDetailCheckSheet.cancel();
                groupDetailCheckSheet=null;
            }
        });
        groupDetailCheckSheet.show();
    }


    private class MemberHolder extends RecyclerView.ViewHolder{
        private TextView memberName;
        private TextView memberNickName;
        private CheckBox checkBox;

        private MemberHolder(View itemView) {
            super(itemView);
            memberName=itemView.findViewById(R.id.group_detail_member_id);
            memberNickName=itemView.findViewById(R.id.group_detail_member_name);
            checkBox=itemView.findViewById(R.id.group_detail_member_check);
            checkBox.setVisibility(View.GONE);
        }
        private void bindInfo(User user,int position,ArrayList<Boolean> booleans,boolean selectAble) {
            memberName.setText(user.getUserAccount());
            memberNickName.setText(user.getUserUsername());
            if(selectAble){
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        booleans.set(position,isChecked);
                    }
                });
            }

        }
    }

    private class MemberAdapter extends RecyclerView.Adapter<MemberHolder> {
        private List<User> infoList;
        private ArrayList<Boolean> booleanList=new ArrayList<>();
        MemberAdapter(List<User> infoList,boolean flag) {
            this.infoList = infoList;
            multiSelect=flag;
            for(int i=0;i<infoList.size();i++){
                booleanList.add(false);
            }
        }
        public List<String> getResultList(){
            List<String> resultList=new ArrayList<>();
            if(multiSelect){
                for(int i=0;i<booleanList.size();i++){
                    if(booleanList.get(i)){
                        resultList.add(infoList.get(i).getUserAccount());
                    }
                }
                return resultList;
            }
            else return resultList;
        }

        @NonNull
        @Override
        public MemberHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(GroupDetailPageActivity.this);
            View view = layoutInflater.inflate(R.layout.group_detail_member_item, viewGroup, false);
            return new MemberHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MemberHolder memberHolder, int i) {
            User info= infoList.get(i);
            memberHolder.bindInfo(info,i,booleanList,multiSelect);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }



    private class CheckHolder extends RecyclerView.ViewHolder{
        private TextView memberName;
        private TextView memberNickName;
        private TextView checkStatus;
        private TextView amount;
        private MaterialButton btnCheck;

        private CheckHolder(View itemView) {
            super(itemView);
            memberName=itemView.findViewById(R.id.group_check_member_id);
            memberNickName=itemView.findViewById(R.id.group_check_member_name);
            checkStatus=itemView.findViewById(R.id.group_check_status);
            amount=itemView.findViewById(R.id.group_check_amount);
            btnCheck=itemView.findViewById(R.id.btn_group_check_check);
        }
        private void bindInfo(GroupResInfo res) {
            memberName.setText(res.getCounter());
            memberNickName.setText(res.getCounterNickname());
            if(res.isCounterStatus()&&res.isMyStatus()){
                checkStatus.setText("Checked");
            }
            else checkStatus.setText("Unchecked");
            if(res.isMyStatus()){
                btnCheck.setVisibility(View.GONE);
            }
            else btnCheck.setVisibility(View.VISIBLE);
            if(res.getNum()>=0){
                amount.setText(convert(res.getNum()));
            }
            else amount.setText("-"+convert(res.getNum()));
            btnCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean ifStart=false;
                    if(res.getNum()>0){
                        ifStart=true;
                    }
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("uh",bindUserHeader());
                    map1.put("uuid",res.getUuid());
                    map1.put("ifStart",ifStart);
                    groupDetailPresenter.checkGroupRes(GroupDetailPageActivity.this,map1);
                }
            });
        }
    }
    private class CheckAdapter extends RecyclerView.Adapter<CheckHolder> {
        private List<GroupResInfo> infoList;
        CheckAdapter(List<GroupResInfo> infoList) {
            this.infoList = infoList;
        }

        @NonNull
        @Override
        public CheckHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(GroupDetailPageActivity.this);
            View view = layoutInflater.inflate(R.layout.group_check_item, viewGroup, false);
            return new CheckHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CheckHolder checkHolder, int i) {
            GroupResInfo info= infoList.get(i);
            checkHolder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }

    public void setUpGroupInfo(int status,boolean confirm){
        if(status==0){
            groupStauts.setText("ACTIVE");
            if(!confirm){
                confirmBtn.setVisibility(View.VISIBLE);
                checkBtn.setVisibility(View.GONE);
                myStatus.setText("Unconfirmed");
            }
            else {
                confirmBtn.setVisibility(View.GONE);
                checkBtn.setVisibility(View.GONE);
                myStatus.setText("Confirmed,waiting for check");
            }
        }
        else if(status==1){
            groupStauts.setText("CONFIRMED");
            confirmBtn.setVisibility(View.GONE);
            checkBtn.setVisibility(View.VISIBLE);
            myStatus.setText("Check");
        }
        else if(status==2){
            groupStauts.setText("CLOSED");
            myStatusText.setVisibility(View.GONE);
            myStatus.setVisibility(View.GONE);
            confirmBtn.setVisibility(View.GONE);
            checkBtn.setVisibility(View.GONE);
        }
    }
    public List<User> userList(){
        List<User> list=new ArrayList<>();
        User user=new User();
        user.setUserUsername("network error");
        user.setUserAccount("network error");
        for(int i=0;i<1;i++){
            list.add(user);
        }
        return list;
    }

    public void LoadDebtList(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.getGroupDebtList(GroupDetailPageActivity.this,map1);
    }

    public void LoadMemberList(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.getGroupMemberList(GroupDetailPageActivity.this,map1);
    }
    public void LoadAddList(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.getGroupAddList(GroupDetailPageActivity.this,map1);
    }
    public void LoadCheckList(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.getGroupResCheckList(GroupDetailPageActivity.this,map1);
    }

    public void confirmGroupRequest(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.confirmGroup(GroupDetailPageActivity.this,map1);
    }
    public void UpdateGroupInfo(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("uh",bindUserHeader());
        map1.put("uuid",groupUuid);
        groupDetailPresenter.updateGroupInfo(GroupDetailPageActivity.this,map1);
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

    private List<GroupDetail> initDefaultList(){
        ArrayList<GroupDetail> defaultInfoList=new ArrayList<GroupDetail>();

        for(int i=0;i<10;i++){
            GroupDetail info3=new GroupDetail();
            info3.setDate("");
            info3.setDesc("network error");
            info3.setNum(000);
            info3.setPropose(true);
            info3.setProposeNickName("");
            defaultInfoList.add(info3);
        }
        return defaultInfoList;
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
}

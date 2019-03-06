package com.example.l.rBOOK.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.l.rBOOK.GlobalData;
import com.example.l.rBOOK.R;
import com.example.l.rBOOK.model.dto.Group;
import com.example.l.rBOOK.presenter.GroupListPresenter;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupListFragment extends Fragment implements GroupListViewInterface {
    @Nullable
    FloatingActionMenu floatingActionMenu;
    private RecyclerView groupRecyclerView;
    private GroupAdapter groupAdapter;
    private GroupListPresenter groupListPresenter;
    private SwipeRefreshLayout groupSwipeRefreshLayout;
    private boolean ifVisible = false;
    private BottomSheetDialog groupNewBottomSheetDialog;
    private BottomSheetDialog groupJoinBottomSheetDialog;
    private static GlobalData globalData;

    public static GroupListFragment newInstance() {
        GroupListFragment groupListFragment = new GroupListFragment();
        Bundle args = new Bundle();
        groupListFragment.setArguments(args);
        return groupListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_list_fragment, container, false);
        globalData=(GlobalData) getActivity().getApplication();
        groupListPresenter=new GroupListPresenter(this);
        groupRecyclerView = view.findViewById(R.id.group_recycler);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupAdapter = new GroupAdapter(initDefaultInfoList());
        groupRecyclerView.setAdapter(groupAdapter);
        LoadList();
        groupSwipeRefreshLayout = view.findViewById(R.id.group_swipe_refresh);
        if (getActivity() != null) {
            floatingActionMenu = getActivity().findViewById(R.id.group_float_menu);
        }
        setUpFloatingActionBtn();
        groupSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        groupSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadList();
            }
        });
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

        return view;
    }

    @Override
    public void getList(List<Group> list)
    {
        groupAdapter = new GroupAdapter(list);
        groupRecyclerView.setAdapter(groupAdapter);
        groupSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(int type){
        groupSwipeRefreshLayout.setRefreshing(false);
        switch (type){
            case 0:
                Toast.makeText(getActivity(), "Add New Group Success", Toast.LENGTH_LONG).show();
                groupNewBottomSheetDialog.cancel();
                LoadList();
                break;
            case 1:
                Toast.makeText(getActivity(), "Add New Group Fail", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "Join Group Success", Toast.LENGTH_LONG).show();
                groupJoinBottomSheetDialog.cancel();
                LoadList();
                break;
            case 3:
                Toast.makeText(getActivity(), "Join Group Fail", Toast.LENGTH_LONG).show();
                break;
            case 4:
                Toast.makeText(getActivity(), "List Load Fail", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private class GroupHolder extends RecyclerView.ViewHolder{
        private RelativeLayout relativeLayout;
        private TextView groupName;
        private TextView groupMemberNum;
        private TextView groupStatus;
        private TextView date;

        private GroupHolder(View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.group_relative_layout);
            groupName=itemView.findViewById(R.id.group_name);
            groupMemberNum=itemView.findViewById(R.id.group_member_num);
            groupStatus=itemView.findViewById(R.id.group_status);
            date=itemView.findViewById(R.id.group_date);
        }
        private void bindInfo(Group group) {

            groupName.setText(group.getGroupName());
            String temp=group.getMemberNum()+"";
            groupMemberNum.setText(temp);
            if(group.getStatus()==0){
                groupStatus.setText("Active");
                groupStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            else if(group.getStatus()==1){
                groupStatus.setText("Confirmed");
                groupStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            else if(group.getStatus()==2){
                groupStatus.setText("Closed");
                groupStatus.setTextColor(getResources().getColor(R.color.half_black));
            }
            date.setText(group.getDate());

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),GroupDetailPageActivity.class );
                    intent.putExtra("groupName", group.getGroupName());
                    intent.putExtra("groupUUID", group.getUuid());
                    intent.putExtra("groupStatus",group.getStatus());
                    intent.putExtra("groupMyConfirm",group.isMyConfirm());
                    startActivity(intent);
                }
            });
        }
    }

    private class GroupAdapter extends RecyclerView.Adapter<GroupHolder> {
        private List<Group> infoList;
        GroupAdapter(List<Group> infoList) {
            this.infoList = infoList;
        }

        @NonNull
        @Override
        public GroupHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.group_item, viewGroup, false);
            return new GroupHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GroupHolder groupHolder, int i) {
            Group info= infoList.get(i);
            groupHolder.bindInfo(info);
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

    private void setUpFloatingActionBtn() {
        if (getActivity() != null && floatingActionMenu != null) {
            getActivity().findViewById(R.id.btn_group_join).setOnClickListener(v -> {
                floatingActionMenu.close(false);
                setUpJoinBottomSheetDialog();
            });

            getActivity().findViewById(R.id.btn_group_new).setOnClickListener(v -> {
                floatingActionMenu.close(true);
                setUpNewBottomSheetDialog();
            });
        }
    }

    public List<Group> initDefaultInfoList() {
        List<Group> defaultInfoList = new ArrayList<>();
        for(int i=0;i<1;i++){
           Group info=new Group();
            info.setDate("");
            info.setStatus(0);
            info.setMyConfirm(false);
            info.setGroupName("network error");
            info.setUuid("");
            info.setMemberNum(0);
            defaultInfoList.add(info);
        }
        return defaultInfoList;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        ifVisible = isVisibleToUser;
    }

    public void setUpNewBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupNewBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.group_new_sheet, null);
        groupNewBottomSheetDialog.setContentView(bottomSheetView);
        groupNewBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        groupNewBottomSheetDialog.setCancelable(true);
        groupNewBottomSheetDialog.setCanceledOnTouchOutside(true);
        EditText desc=bottomSheetView.findViewById(R.id.group_new_edit_desc);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_new_cancel);
        MaterialButton submit=bottomSheetView.findViewById(R.id.btn_group_new_submit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupNewBottomSheetDialog.cancel();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!desc.getText().toString().equals("")){
                    Map<String,Object> map=new HashMap<>();
                    map.put("uh",bindUserHeader());
                    map.put("groupName",desc.getText().toString());
                    groupListPresenter.sendNewGroupRequest(getActivity(),map);
                }
                else Toast.makeText(getActivity(), "Information Incomplete", Toast.LENGTH_LONG).show();
            }
        });
        groupNewBottomSheetDialog.show();
    }

    public void setUpJoinBottomSheetDialog(){
        floatingActionMenu.close(true);
        groupJoinBottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.group_join_sheet, null);
        groupJoinBottomSheetDialog.setContentView(bottomSheetView);
        groupJoinBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet).setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        groupJoinBottomSheetDialog.setCancelable(true);
        groupJoinBottomSheetDialog.setCanceledOnTouchOutside(true);
        EditText id=bottomSheetView.findViewById(R.id.group_join_edit_id);
        MaterialButton btnCancel = bottomSheetView.findViewById(R.id.btn_group_join_cancel);
        MaterialButton submit=bottomSheetView.findViewById(R.id.btn_group_join_submit);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupJoinBottomSheetDialog.cancel();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!id.getText().toString().equals("")){
                    Map<String,Object> map=new HashMap<>();
                    map.put("uh",bindUserHeader());
                    map.put("uuid",id.getText().toString());
                    groupListPresenter.sendJoinGroupRequest(getActivity(),map);
                }
                else Toast.makeText(getActivity(), "Information Incomplete", Toast.LENGTH_LONG).show();
            }
        });
        groupJoinBottomSheetDialog.show();
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
        groupListPresenter.loadList(getActivity(),map);
    }
}

package com.example.l.rBOOK;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.l.rBOOK.view.CardFragment;
import com.example.l.rBOOK.view.DebtListFragment;
import com.example.l.rBOOK.view.GroupListFragment;
import com.example.l.rBOOK.view.LoginActivity;
import com.example.l.rBOOK.view.MainPagerAdapter;
import com.example.l.rBOOK.view.WelcomeFragment;
import com.github.clans.fab.FloatingActionMenu;
import com.github.clans.fab.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.main_page_toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.group_float_menu)
    FloatingActionMenu groupFloatingActionMenu;
    @BindView(R.id.btn_debt_add)
    FloatingActionButton debtAddBtn;
    GlobalData globalData;
    private long exitFlag = 0;
    private MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        globalData = (GlobalData) getApplication();
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        debtAddBtn.setVisibility(View.GONE);
        groupFloatingActionMenu.setVisibility(View.GONE);
        setUpViewPager();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        debtAddBtn.setVisibility(View.GONE);
                        groupFloatingActionMenu.setVisibility(View.GONE);
                        break;
                    case 1:
                        debtAddBtn.setVisibility(View.VISIBLE);
                        groupFloatingActionMenu.setVisibility(View.GONE);
                        break;
                    case 2:
                        groupFloatingActionMenu.setVisibility(View.VISIBLE);
                        debtAddBtn.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (groupFloatingActionMenu.isOpened()) {
                    groupFloatingActionMenu.close(true);
                }
                groupFloatingActionMenu.setVisibility(View.GONE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager() {
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.addFragment(WelcomeFragment.newInstance(), "Total");
        mainPagerAdapter.addFragment(DebtListFragment.newInstance(), "Debt");
        mainPagerAdapter.addFragment(GroupListFragment.newInstance(), "Group");
        viewPager.setAdapter(mainPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent=new Intent(this,LoginActivity.class);
                instance.finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitFlag > 2000) {
            Toast.makeText(this, "Press button Again to Exit", Toast.LENGTH_SHORT).show();
            exitFlag = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        groupFloatingActionMenu.showMenu(true);
    }

}

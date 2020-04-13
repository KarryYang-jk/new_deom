package com.example.app_object.mvp.ui.activity;


import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.example.app_object.R;
import com.example.app_object.base.BaseActivity;
import com.example.app_object.mvp.ui.adapter.HomeAdapter;
import com.example.app_object.mvp.ui.fragment.HomeFragmet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.home_navigation)
    BottomNavigationView mBottomNv;

    @Override
    protected void initListenner() {
        mBottomNv.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_home:
                    Log.e("TAG", "00000000");
                    switchTab(0);
                    break;
                case R.id.item_navigation:
                    Log.e("TAG", "111111111");
                    switchTab(1);
                    break;
                case R.id.item_tixi:
                    Log.e("TAG", "2222222222");
                    switchTab(2);
                    break;
                case R.id.item_gongzhonghao:
                    Log.e("TAG", "4444444444");
                    switchTab(3);
                    break;
            }
            return true;
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当ViewPager页面切换的时候让下面的tab标签跟着切换
                mBottomNv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void switchTab(int i){
        mViewPager.setCurrentItem(i);
    }

    @Override
    protected void onViewCreated() {
        List<HomeFragmet> fragments = getHomeFragments();
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(homeAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    //当ViewPager页面切换的时候让下面的tab标签跟着切换
                mBottomNv.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<HomeFragmet> getHomeFragments() {
        List<HomeFragmet> fragments = new ArrayList<HomeFragmet>();
        for (int i = 0; i < mBottomNv.getChildCount(); i++) {
            HomeFragmet homeFragment = new HomeFragmet(i);
            fragments.add(homeFragment);
        }
        return fragments;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

}

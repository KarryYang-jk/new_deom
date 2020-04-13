package com.example.mvp_4_13;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mvp_4_13.adap.HomeVpAdapter;
import com.example.mvp_4_13.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager mHomeViewpager;
    private BottomNavigationView mHomeNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void initListenner() {

        mHomeNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.item_home:
                    switchTab(0);
                    break;
                case R.id.item_navigation:
                    switchTab(1);
                    break;
                case R.id.item_tixi:
                    switchTab(2);
                    break;
                case R.id.item_gongzhonghao:
                    switchTab(3);
                    break;
            }
            return true;
        });
        mHomeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当ViewPager页面切换的时候让下面的tab标签跟着切换
                mHomeNavigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void switchTab(int i) {
        mHomeViewpager.setCurrentItem(i);
    }

    @Override
    protected void onViewCreated() {
        List<HomeFragmet> fragments = getHomeFragmets();
        HomeVpAdapter adapter = new HomeVpAdapter(getSupportFragmentManager(), fragments);
        mHomeViewpager.setAdapter(adapter);
    }

    private List<HomeFragmet> getHomeFragmets() {
        List<HomeFragmet> fragments = new ArrayList<HomeFragmet>();
        for (int i = 0; i < 4; i++) {
            HomeFragmet homeFragmet = new HomeFragmet(i);
            fragments.add(homeFragmet);
        }
        return fragments;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private void initView() {
        mHomeViewpager = (ViewPager) findViewById(R.id.home_viewpager);
        mHomeNavigation = (BottomNavigationView) findViewById(R.id.home_navigation);
    }
}

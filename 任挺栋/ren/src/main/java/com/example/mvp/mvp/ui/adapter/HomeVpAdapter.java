package com.example.mvp.mvp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mvp.mvp.ui.fragment.HomeFragmet;

import java.util.List;

public class HomeVpAdapter extends FragmentPagerAdapter {
    private List<HomeFragmet> mFragments;

    public HomeVpAdapter(@NonNull FragmentManager fm, List<HomeFragmet> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

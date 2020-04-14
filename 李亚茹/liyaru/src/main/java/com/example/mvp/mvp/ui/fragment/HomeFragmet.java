package com.example.mvp.mvp.ui.fragment;

import android.util.Log;

import com.example.mvp.R;
import com.example.mvp.base.BaseFragment;
import com.example.mvp.base.BasePresenter;
import com.example.mvp.base.IBasePresenter;
import com.example.mvp.base.IBaseView;
import com.example.mvp.mvp.presenter.HomePresenter;
import com.example.mvp.mvp.ui.common.LazyFragment;

public class HomeFragmet extends LazyFragment {


    private int mType;

    public HomeFragmet(int type) {
        this.mType = type;
    }

    @Override
    protected void stopLazyLoad() {
        switch (mType) {
            case 0:
                Log.e("TAG", "1Frgment停止加载");
                break;
            case 1:
                Log.e("TAG", "2Frgment停止加载");
                break;
            case 2:
                Log.e("TAG", "3Frgment停止加载");
                break;
            case 3:
                Log.e("TAG", "4Frgment停止加载");
                break;
        }
    }

    @Override
    protected void lazyLoad() {
        getmPresenter().start(mType);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return new HomePresenter(this);
    }


    protected int getLayoutId() {
        Integer layoutId = switchLayout(mType);
        if (layoutId != null)
            return layoutId;
        return 0;
    }

    private static Integer switchLayout(int mType) {
        switch (mType) {
            case 0:
                return R.layout.fragment_home;
            case 1:
                return R.layout.fragment_navigation;
            case 2:
                return R.layout.fragment_tixi;

            case 3:
                return R.layout.fragment_gongzhonghao;
        }
        return null;
    }

    @Override
    public void stateScuess(Object o) {

    }

    @Override
    public void stateError(String msg) {

    }
}

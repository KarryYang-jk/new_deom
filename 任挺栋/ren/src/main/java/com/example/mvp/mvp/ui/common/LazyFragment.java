package com.example.mvp.mvp.ui.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvp.base.BaseFragment;
import com.example.mvp.base.BasePresenter;

public abstract class LazyFragment extends BaseFragment {

    //Fragment是否被初始化了  默认没有被初始化
    private boolean mInitView = false;

    //Fragment是否加载完数据了 默认没有加载数据
    private boolean mHasLoadMore = false;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInitView = true;
        initLazyLoad();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initLazyLoad();
    }

    protected void initLazyLoad() {
        //如果Fragment的View没有被初始化 就return出方法
        if (!mInitView)
            return;
        //Fragment对用户可见
        if (getUserVisibleHint()) {
            lazyLoad();
            //代表加载完数据了
            mHasLoadMore = true;
            //Fragment代表对用户不可见
        } else {
            //如果有数据了，才停止加载
            if (mHasLoadMore) {
                stopLazyLoad();
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInitView = false;
        mHasLoadMore = false;
    }

    protected abstract void stopLazyLoad();

    protected abstract void lazyLoad();


    @Override
    protected abstract void initInject();

    @Override
    protected abstract BasePresenter createPresenter();

    @Override
    protected abstract int getLayoutId();

    @Override
    public abstract void stateScuess(Object o);

    @Override
    public abstract void stateError(String msg);

}

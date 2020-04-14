package com.example.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * V层对象持有P层引用   作用：1.拿到P层对象向P层发送请求数据的指令 （让具体的实现类去请求对应的数据）
 * 2.拿到P层对象关联V层生命周期  3.拿到P层对象释放V层引用
 *
 * @param <P>
 * @param <T>
 */
public abstract class BaseFragment<P extends BasePresenter, T>
        extends Fragment implements IBaseView<T> {
    private Unbinder unbinder;
    private P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View view = null;
        if (layoutId != 0)
            view = inflater.inflate(layoutId, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (mPresenter == null)
            mPresenter = createPresenter();

        //2.P层关联V层的生命周期
        mPresenter.attachView(this);

        initInject();
    }


    protected abstract void initInject();


    protected abstract P createPresenter();

    protected abstract int getLayoutId();


    public P getmPresenter() {
        if (mPresenter != null)
            return mPresenter;
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        //3.P层释放V层引用
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }
}

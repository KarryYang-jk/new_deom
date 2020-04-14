package com.example.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayout();
        if (layoutId != 0)
            setContentView(layoutId);
        App.getInstance().addActivity(this);
        unbinder = ButterKnife.bind(this);
        onViewCreated();
        initListenner();
    }

    protected abstract void initListenner();

    protected abstract void onViewCreated();

    protected abstract int getLayout();

    @Override
    public void stateScuess(T t) {

    }
    @Override
    public void stateError(String msg) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        App.getInstance().removeActivity();
    }
}

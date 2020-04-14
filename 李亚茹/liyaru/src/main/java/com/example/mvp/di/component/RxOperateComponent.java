package com.example.mvp.di.component;

import com.example.mvp.di.annotation.PerSinglelton;
import com.example.mvp.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface RxOperateComponent {
    void inject(RxOperateImpl rxOperate);
}

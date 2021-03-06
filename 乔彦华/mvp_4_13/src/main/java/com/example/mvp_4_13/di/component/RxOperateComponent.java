package com.example.mvp_4_13.di.component;

import com.example.mvp.di.annotation.PerSinglelton;
import com.example.mvp.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface RxOperateComponent {
    void inject(RxOperateImpl rxOperate);
}

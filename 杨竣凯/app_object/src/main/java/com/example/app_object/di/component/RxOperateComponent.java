package com.example.app_object.di.component;


import com.example.app_object.di.annotation.PerSinglelton;
import com.example.app_object.mvp.model.RxOperateImpl;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface RxOperateComponent {
    void inject(RxOperateImpl rxOperate);
}

package com.example.mvp.di.component;


import com.example.mvp.di.annotation.PerSinglelton;
import com.example.mvp.mvp.model.OkManagerModule;
import com.example.mvp.mvp.presenter.HomePresenter;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomePresenter homePresenter);
}

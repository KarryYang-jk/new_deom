package com.example.app_object.di.component;



import com.example.app_object.di.annotation.PerSinglelton;
import com.example.app_object.mvp.presenter.HomePresenter;

import dagger.Component;

@PerSinglelton
@Component(dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomePresenter homePresenter);
}

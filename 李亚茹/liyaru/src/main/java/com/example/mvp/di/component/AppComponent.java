package com.example.mvp.di.component;

import android.content.SharedPreferences;

import com.example.mvp.base.IBaseView;
import com.example.mvp.di.module.AppModule;
import com.example.mvp.mvp.model.OkManagerModule;
import com.example.mvp.mvp.model.api.ApiService;
import com.example.mvp.mvp.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

//专门对外提供单例(SP,OK,OkHttClient.Builder ,Retrofit和RetrofitBuidelr ApiService)的桥梁
@Singleton
@Component(modules = {AppModule.class, OkManagerModule.class})
public interface AppComponent {
    SharedPreferences provideSp();
    OkHttpClient provideOk();
    ApiService provideApiService();
}

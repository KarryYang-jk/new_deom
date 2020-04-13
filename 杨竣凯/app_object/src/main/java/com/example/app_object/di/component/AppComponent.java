package com.example.app_object.di.component;

import android.content.SharedPreferences;

import com.example.app_object.di.module.AppModule;
import com.example.app_object.mvp.model.OkManagerModule;
import com.example.app_object.mvp.model.api.ApiService;

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

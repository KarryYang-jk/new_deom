package com.example.mvp_4_13.di.module;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.mvp_4_13.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSp() {
        return mApp.getSharedPreferences("config", Context.MODE_PRIVATE);
    }


}

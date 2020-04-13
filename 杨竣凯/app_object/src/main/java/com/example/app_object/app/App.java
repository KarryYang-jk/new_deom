package com.example.app_object.app;

import android.app.Activity;
import android.app.Application;

import com.example.app_object.di.component.DaggerAppComponent;
import com.example.app_object.di.module.AppModule;

import java.util.HashSet;
import java.util.Set;

public class App extends Application {
    private static App mInstance;
    private Set<Activity> mActivities;
    private static DaggerAppComponent mDaggerAppCompat;
    @Override

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initInject();
    }

    private void initInject() {
        if (mDaggerAppCompat == null)
            mDaggerAppCompat = (DaggerAppComponent) DaggerAppComponent.builder().
                    appModule(new AppModule(this)).build();

    }
    public static DaggerAppComponent daggerAppComponent() {
        return mDaggerAppCompat;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }


    public void addActivity(Activity act) {
        if (mActivities == null)
            mActivities = new HashSet<Activity>();
        mActivities.add(act);
    }

    public void removeActivity() {
        if (mActivities != null) {
            for (Activity act : mActivities) {
                act.finish();
            }
        }
        System.exit(0);
    }
}

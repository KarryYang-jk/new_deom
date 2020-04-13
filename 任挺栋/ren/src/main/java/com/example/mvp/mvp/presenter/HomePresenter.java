package com.example.mvp.mvp.presenter;

import android.util.Log;

import com.example.mvp.app.App;
import com.example.mvp.base.BasePresenter;
import com.example.mvp.di.component.DaggerAppComponent;
import com.example.mvp.di.component.DaggerHomeComponent;
import com.example.mvp.mvp.contract.Info;
import com.example.mvp.mvp.model.api.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomePresenter extends BasePresenter {
    @Inject
    OkHttpClient okHttpClient;

    public HomePresenter() {
        DaggerHomeComponent.builder().
                appComponent(App.daggerAppComponent()).
                build().
                inject(this);
    }

    //向M层请求数据
    @Override
    public void start(Object obj) {
        super.start(obj);
        Request build = new Request.Builder().url(Constants.CHAPTERS_LIST).get().build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Info info = new Gson().fromJson(string, Info.class);
                String name = info.getData().get(0).getName();
                Log.e("TAG", "onResponse: "+name);
            }
        });
        Log.e("TAG", okHttpClient.toString());
        if (obj instanceof Integer) {
            Integer type = (Integer) obj;
            switch (type) {
                case 0:

                    Log.e("TAG", "第一个Fragment开始加载数据了....");
                    break;
                case 1:
                    Log.e("TAG", "第二个Fragment开始加载数据了....");
                    break;
                case 2:
                    Log.e("TAG", "第三个Fragment开始加载数据了...");
                    break;
                case 3:
                    Log.e("TAG", "第四个Fragment开始加载数据了....");
                    break;
            }
        }
    }
}

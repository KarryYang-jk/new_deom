package com.example.app_object.mvp.presenter;

import android.util.Log;


import com.example.app_object.app.App;
import com.example.app_object.base.BasePresenter;
import com.example.app_object.di.component.DaggerHomeComponent;
import com.example.app_object.mvp.contract.ChaptersListInfo;
import com.example.app_object.mvp.model.api.Constants;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

/******
 *
 *
 *
 * 杨竣凯独有代码拒绝抄袭
 *
 *
 *
 *
 * **/
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                Log.e("TAG","----------------------------------------------------"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                ChaptersListInfo chaptersListInfo = new Gson().fromJson(string, ChaptersListInfo.class);
                String name = chaptersListInfo.getData().get(0).getName();
                Log.e("TAG","---------------------------------------------"+name);
            }
        });

        Log.e("TAG", this.okHttpClient.toString());
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

package com.example.app_object.mvp.presenter;

import android.util.Log;


import com.example.app_object.app.App;
import com.example.app_object.base.BasePresenter;
import com.example.app_object.callback.IDataCallBack;
import com.example.app_object.di.component.DaggerHomeComponent;
import com.example.app_object.mvp.contract.ChaptersListInfo;
import com.example.app_object.mvp.model.RxOperateImpl;
import com.example.app_object.mvp.model.api.Constants;
import com.example.app_object.mvp.ui.fragment.HomeFragmet;
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
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;


public class HomePresenter extends BasePresenter {
    @Inject
    OkHttpClient okHttpClient;
    private HomeFragmet mHomeFragment;
    private RxOperateImpl rxOperate;

    public HomePresenter() {
        DaggerHomeComponent.builder().
                appComponent(App.daggerAppComponent()).
                build().
                inject(this);
    }

    public HomePresenter(HomeFragmet homeFragmet) {
        this.mHomeFragment=homeFragmet;
        rxOperate = new RxOperateImpl();
    }

    //向M层请求数据
    @Override
    public void start(Object obj) {
        super.start(obj);
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
                    rxOperate.requestData(Constants.CHAPTERS_LIST, new IDataCallBack<Object>() {

                        @Override
                        public void onStateSucess(Object o) {
                                if (o instanceof ResponseBody){
                                    ResponseBody body = (ResponseBody) o;
                                    String jsonStr = null;
                                    try {
                                        jsonStr = body.string();
                                        ChaptersListInfo chaptersListInfo = new Gson().fromJson(jsonStr, ChaptersListInfo.class);
                                        mHomeFragment.stateScuess(chaptersListInfo);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        //交给V层更新UI
                                        mHomeFragment.stateError(e.getMessage());
                                    }
                                }
                        }

                        @Override
                        public void onStateError(String msg) {

                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {

                        }
                    });
                    break;

            }
        }
    }
}

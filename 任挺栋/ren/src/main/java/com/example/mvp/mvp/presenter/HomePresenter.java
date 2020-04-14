package com.example.mvp.mvp.presenter;

import android.util.Log;

import com.example.mvp.app.App;
import com.example.mvp.base.BasePresenter;
import com.example.mvp.callback.IDataCallBack;
import com.example.mvp.di.component.DaggerAppComponent;
import com.example.mvp.di.component.DaggerHomeComponent;
import com.example.mvp.mvp.contract.Info;
import com.example.mvp.mvp.model.RxOperateImpl;
import com.example.mvp.mvp.model.api.Constants;
import com.example.mvp.mvp.ui.fragment.HomeFragmet;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomePresenter extends BasePresenter {
    @Inject
    OkHttpClient okHttpClient;
    private RxOperateImpl mImpl;
    private HomeFragmet mhomeFragmet;

    public HomePresenter() {
        DaggerHomeComponent.builder().
                appComponent(App.daggerAppComponent()).
                build().
                inject(this);
    }

    public HomePresenter(HomeFragmet homeFragmet) {
        this.mhomeFragmet=homeFragmet;
        mImpl=new RxOperateImpl();
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
                    mImpl.requestData(Constants.CHAPTERS_LIST, new IDataCallBack<Object>() {
                        @Override
                        public void onStateSucess(Object t) {
                            if (t instanceof ResponseBody){
                                ResponseBody body = (ResponseBody) t;
                                String string=null;
                                try {
                                    string = body.string();
                                    Log.e("TAG", "onStateSucess: "+"=======" );
                                    Gson gson = new Gson();
                                    Info info = gson.fromJson(string, Info.class);
                                    mhomeFragmet.stateScuess(info);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            mhomeFragmet.stateError(msg);
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            addDisposable(disposable);
                        }
                    });
                    break;
            }
        }
    }
}

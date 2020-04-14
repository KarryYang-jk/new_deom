package com.example.mvp.mvp.presenter;

import android.util.Log;

import com.example.mvp.app.App;
import com.example.mvp.base.BasePresenter;
import com.example.mvp.callback.IDataCallBack;
import com.example.mvp.mvp.model.RxOperateImpl;
import com.example.mvp.mvp.ui.fragment.HomeFragmet;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

public class HomePresenter<T> extends BasePresenter {


    private RxOperateImpl mImpl;
    private HomeFragmet mHomeFragment;

    public HomePresenter(HomeFragmet homeFragmet) {
        this.mHomeFragment = homeFragmet;
        mImpl = new RxOperateImpl();
    }

    //向M层请求数据
    @Override
    public void start(Object obj) {
        super.start(obj);
        if (obj instanceof Integer) {
            Integer type = (Integer) obj;
            switch (type) {
                case 0:  //第一个Fragment请求数据
                    mImpl.requestData("https://wanandroid.com/wxarticle/chapters/json", new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody) {
                                ResponseBody body = (ResponseBody) t;
                                String jsonStr = null;
                                try {
                                    jsonStr = body.string();
                                    Log.e("TAG", jsonStr + "===");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            mHomeFragment.stateError(msg);
                        }
                        @Override
                        public void onResponseDisposable(Disposable disposable) {

                            addDisposable(disposable);
                        }
                    });
                    break;
                case 1:
                    Log.e("TAG", "第二个Fragment开始加载数据了....");
                    break;
                case 2:
                    Log.e("TAG", "第三个Fragment开始加载数据了...");
                    break;
                case 3:
                    //第四个Fragment开始请求数据
                    mImpl.requestData("https://www.wanandroid.com/banner/json", new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody) {
                                ResponseBody body = (ResponseBody) t;
                                String jsonStr = null;
                                try {
                                    jsonStr = body.string();
                                    Log.e("公众号", jsonStr + "===");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            mHomeFragment.stateError(msg);
                        }
                        @Override
                        public void onResponseDisposable(Disposable disposable) {

                            addDisposable(disposable);
                        }
                    });
                    Log.e("TAG", "第四个Fragment开始加载数据了....");
                    break;

            }
        }
    }
}

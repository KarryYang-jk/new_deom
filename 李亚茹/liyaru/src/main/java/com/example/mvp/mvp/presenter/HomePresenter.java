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
import okhttp3.RequestBody;
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
                                    //TODO GSON解析 解析完之后 将解析的结果交给V层更新UI
                                    //交给V层更新UI
                                    //mHomeFragment.stateScuess(t);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //交给V层更新UI
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            //交给V层更新UI
                            mHomeFragment.stateError(msg);
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            //添加获取的网络开关
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
                    mImpl.requestData("http://www.wanandroid.com/project/list/0/json", new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody){
                                ResponseBody body = (ResponseBody) t;
                                String jsonStr = null;
                                try {
                                    jsonStr = body.string();
                                    Log.e("TAG", jsonStr + "===");
                                    //TODO GSON解析 解析完之后 将解析的结果交给V层更新UI
                                    //交给V层更新UI
                                    //mHomeFragment.stateScuess(t);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //交给V层更新UI
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            //交给V层更新UI
                            mHomeFragment.stateError(msg);
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

package com.example.mvp.mvp.model;

import android.text.TextUtils;
import android.util.Log;

import com.example.mvp.app.App;
import com.example.mvp.callback.IDataCallBack;
import com.example.mvp.callback.ProgressDataCallback;
import com.example.mvp.di.component.DaggerRxOperateComponent;
import com.example.mvp.mvp.model.api.ApiService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * RxJava和OkHttp关联的封装类
 */
public class RxOperateImpl {
    @Inject
    ApiService mApiService;

    public RxOperateImpl() {
        //  将apiService注入到RxOperateImpl里面
        DaggerRxOperateComponent.builder().
                appComponent(App.daggerAppComponent()).build().
                inject(this);
    }


    public <T> void requestData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator
                (mApiService.requestData(url)).
                subscribe(getObserver(dataCallBack));
    }



    public <T> void requestData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator
                    (mApiService.requestData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }



    public <T> void requestFormData(String url, IDataCallBack<T> dataCallBack) {
        RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url)).
                subscribe(getObserver(dataCallBack));

    }



    public <T> void requestFormData(String url, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (params == null || params.size() == 0)
            requestFormData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, params)).
                    subscribe(getObserver(dataCallBack));
    }



    public <T> void requestFormData(String url, HashMap<String, T> headers, IDataCallBack<T> dataCallBack) {
        if (headers == null || headers.size() == 0)
            requestData(url, dataCallBack);
        else
            RxSchedulersOperator.retryWhenOperator(
                    mApiService.requestFormData(url, headers)).
                    subscribe(getObserver(dataCallBack));
    }



    public <T> void requestFormData(String url, Map<String, T> headers, Map<String, T> params, IDataCallBack<T> dataCallBack) {
        if (headers == null || headers.size() == 0)  //请求头为空 但是参数不为空
            requestFormData(url, params, dataCallBack);
        else if (params == null || params.size() == 0)  //TODO参数为空但是请求头不为空
            requestFormData(url, headers, dataCallBack);
        else if ((headers == null || headers.size() == 0) && // 请求头和参数都为空
                (params == null || params.size() == 0))
            requestFormData(url, dataCallBack);
        else
            //请求头和参数都不为空
            RxSchedulersOperator.retryWhenOperator(mApiService.requestFormData(url, headers, params)).
                    subscribe(getObserver(dataCallBack));
    }


    public <T> void requestFormData(String url, String jsonStr, IDataCallBack<T> dataCallBack) {
        if (jsonStr.isEmpty())
            return;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
        RxSchedulersOperator.retryWhenOperator
                (mApiService.requestFormData(url, requestBody)).
                subscribe(getObserver(dataCallBack));
    }



    public <T> void requestFormData(String url, Map<String, T> haders, String jsonStr, IDataCallBack<T> dataCallBack) {
        if (haders == null || haders.size() == 0)
            requestFormData(url, jsonStr, dataCallBack);
        else { //有请求头并且上传json串的post请求
            if (jsonStr.isEmpty())
                return;
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
            RxSchedulersOperator.retryWhenOperator
                    (mApiService.requestFormData(url, haders, requestBody)).
                    subscribe(getObserver(dataCallBack));
        }
    }



    public <T> void downloadFile(String url, boolean isProcess,
                                 ProgressDataCallback<T> progressDataCallback) {
        if (isProcess) {
            //TODO 需封装带进度的RxJava+Retrofit代码  作业

        } else
            RxSchedulersOperator.retryWhenOperator(mApiService.downloadFile(url)).
                    subscribe(getObserver(progressDataCallback));
    }

    //单个文件上传
    public <T> void uploadSingleFile(String url, File file, IDataCallBack<T> dataCallBack) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);


        MultipartBody.Part part = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
        RxSchedulersOperator.retryWhenOperator(mApiService.uploadSingleFile(url, part)).
                subscribe(getObserver(dataCallBack));
    }


    public <T> void uploadStrFile(String url, File file, String str, IDataCallBack<T> dataCallBack) {

        if (TextUtils.isEmpty(str))
            uploadSingleFile(url, file, dataCallBack);
        else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("aFile", file.getName(), requestBody);
            RequestBody strBody = RequestBody.create(MediaType.parse("multipart/form-data"), str);
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFile(url, strBody, part)).
                    subscribe(getObserver(dataCallBack));
        }
    }


    //多个文件上传方法2
    public <T> void uploadMultipleFiles(String url, IDataCallBack<T> dataCallBack
            , File... files) {
        if (files != null && files.length > 0) {
            Map<String, RequestBody> filesMap = new HashMap<String, RequestBody>();
            for (File file : files) {
                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                filesMap.put("file;filename=" + file.getName(), body);
            }
            RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url, filesMap)).
                    subscribe(getObserver(dataCallBack));
        }
    }


    //多文件上传方法1
    public <T> void uploadMultipleFiles(IDataCallBack<T> dataCallBack, String url, File... files) {
        MultipartBody.Part[] parts = new MultipartBody.Part[files.length];
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                parts[i] = part;
            }
        }
        RxSchedulersOperator.retryWhenOperator(mApiService.uploadMultipleFiles(url, parts)).
                subscribe(getObserver(dataCallBack));
    }


    //多文件+多个键值对上传
    public <T> void uploadStrFiles(String url, Map<String, String> params, IDataCallBack<T> dataCallBack,
                                   File... files) {
        if (files != null && files.length > 0) {

            if (params == null || params.size() == 0) {
                //不带参数的多文件上传
                uploadMultipleFiles(url, dataCallBack, files);
            } else {
                FormBody.Builder builder = new FormBody.Builder();
                for (int i = 0; i < params.size(); i++) {
                    Set<String> keys = params.keySet();
                    for (String key : keys) {
                        String value = params.get(key);
                        //上传多个键值对
                        builder.add(key, value);
                    }
                }
                Map<String, RequestBody> filesMap = new HashMap<String, RequestBody>();
                for (File file : files) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    //上传多个文件
                    filesMap.put("file;fileName=" + file.getName(), requestBody);
                }
                RxSchedulersOperator.retryWhenOperator(mApiService.uploadStrFiles(url, builder.build(), filesMap)).
                        subscribe(getObserver(dataCallBack));

            }
        }
    }



    private <T> RxObserver<? extends T> getObserver(IDataCallBack<T> dataCallBack) {
        return new RxObserver<T>(dataCallBack) {
            @Override
            public void onSubscribe(Disposable d) {
                if (dataCallBack != null)
                    dataCallBack.onResponseDisposable(d);

            }

            @Override
            public void onNext(T t) {
                if (dataCallBack != null)
                    dataCallBack.onStateSucess(t);
            }
        };
    }

}

package com.example.mvp.mvp.model;

import android.util.Log;

import com.example.mvp.callback.IDataCallBack;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RxObserver<T> implements Observer<T> {
    private IDataCallBack<T> mIDataCallBack;

    public RxObserver(IDataCallBack<T> iDataCallBack) {
        this.mIDataCallBack = iDataCallBack;
    }

    @Override
    public abstract void onSubscribe(Disposable d);

    @Override
    public abstract void onNext(T t);


    @Override
    public void onError(Throwable e) {
        //TODO 今天第一作业 全局异常捕获 获取这些异常之后通过IO流写进本地文件， 上传文件的方法(Retrfit2.0)
        if (e instanceof SocketTimeoutException) {
            mIDataCallBack.onStateError("SocketTimeoutException");
            Log.e("TAG", "SocketTimeoutException");
        } else if (e instanceof SocketException) {
            mIDataCallBack.onStateError("SocketException");
            Log.e("TAG", "SocketException");
        } else if (e instanceof UnknownHostException) {
            mIDataCallBack.onStateError("UnknownHostException");
            Log.e("TAG", "UnknownHostException");
        } else if (e instanceof ConnectException) {
            mIDataCallBack.onStateError("ConnectException");
            Log.e("TAG", "ConnectException");
        } else {
            if (e != null) {
                Log.e("TAG", e.getMessage() + "==");
                mIDataCallBack.onStateError(e.getMessage());
            } else {
                Log.e("TAG", "空......");
            }
        }

    }

    @Override
    public void onComplete() {
        Log.e("TAG", "data complete...");
    }
}

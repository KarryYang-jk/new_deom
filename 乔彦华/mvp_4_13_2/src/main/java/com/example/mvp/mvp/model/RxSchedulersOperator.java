package com.example.mvp.mvp.model;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mvp.callback.IObserableListenner;
import com.jakewharton.rxbinding2.view.RxView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 封装RxJava线程切换 和操作符
 */
public class RxSchedulersOperator<T> {
    private static int currentConnectCount = 0;
    private static int connectCount = 10;
    private static long retryTime;

    //线程切换封装的方法
    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
    }

    //封装retryWhen操作符  (错误链接10次 如果10次没链上就认定为链接超时)
    public static <T> ObservableSource<T> retryWhenOperator(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).

                        retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws
                            Exception {

                        return throwableObservable.flatMap(
                                new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                        if (throwable instanceof IOException) {

                                            if (currentConnectCount < connectCount) {
                                                currentConnectCount++;
                                                Log.e("TAG", "网络链接失败，正在进行第" + currentConnectCount + "次重连");
                                                retryTime = 1000 + currentConnectCount * 500;
                                                return Observable.just(1).delay(retryTime, TimeUnit.MILLISECONDS);
                                            } else {
                                                return Observable.error(new Throwable("已经错误重连10次了，一直没链上，请尝试换个网试试"));
                                            }

                                        } else {

                                            return Observable.error(new Throwable("不属于IO网络链接异常," + throwable.getMessage()));
                                        }
                                    }
                                });
                    }
                }).observeOn(AndroidSchedulers.mainThread());

    }


    public static <T> ObservableSource<T> flatMapOprator(Observable<T> observable1, Observable<T> observable2,
                                                         IObserableListenner<T> listenner) {

        return observable1.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                doOnNext(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if (listenner != null)
                            listenner.getObserable1Data(t);
                    }
                }).

                        observeOn(Schedulers.io()).

                        flatMap(new Function<T, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(T translation1) throws Exception {
                                return observable2;
                            }
                        }).observeOn(AndroidSchedulers.mainThread());
    }



    public static <T> ObservableSource<T> concurrenRequest(Observable<T> o1, Observable<T> o2, Observable<T> o3) {
        //每次事件发送6个数字，总共发送3个事件
        return Observable.merge(o1, o2, o3).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }



    public static <T> ObservableSource<T> concurrenRequest(Observable<T>... observables) {
        return Observable.mergeArray(observables).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }



    public static <T> Observable<Object> requestFirstData(View view) {
        return RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }


}

package com.example.mvp_4_13.base;

/**
 * P层接口
 */
public interface IBasePresenter<T> {
    void start();
    void start(T t);
}

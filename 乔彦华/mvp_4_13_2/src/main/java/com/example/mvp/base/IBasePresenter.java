package com.example.mvp.base;

/**
 * P层接口
 */
public interface IBasePresenter<T> {
    void start();
    void start(T t);
}

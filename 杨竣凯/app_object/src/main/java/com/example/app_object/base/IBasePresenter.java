package com.example.app_object.base;

/**
 * P层接口
 */
public interface IBasePresenter<T> {
    void start();
    void start(T t);
}

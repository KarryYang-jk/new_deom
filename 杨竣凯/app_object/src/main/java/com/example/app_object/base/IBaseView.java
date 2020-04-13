package com.example.app_object.base;

/**
 * View层接口
 */
public interface IBaseView<T> {
    void stateScuess(T t);
    void stateError(String msg);
}

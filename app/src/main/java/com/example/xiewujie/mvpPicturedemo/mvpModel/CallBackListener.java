package com.example.xiewujie.mvpPicturedemo.mvpModel;

public interface CallBackListener<T,V> {
    void onSucceed(T data);
    void onFail(V msg);
}

package com.example.xiewujie.mvpPicturedemo.view;

public interface MyMvpView<T,V> extends BaseView {

     void onFail(V msg);

     void setData(T data);
}

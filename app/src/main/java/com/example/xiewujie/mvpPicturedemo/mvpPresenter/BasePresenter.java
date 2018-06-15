package com.example.xiewujie.mvpPicturedemo.mvpPresenter;

import android.content.Context;

import com.example.xiewujie.mvpPicturedemo.view.MyMvpView;

public class BasePresenter<T extends MyMvpView> {
    protected T mvpView;
    protected Context mContext;

   public void attachView(T mvpView,Context context){
        this.mvpView = mvpView;
        mContext = context;
    }

    public void detachView(){
       this.mvpView = null;
    }

    public T getView(){
       return mvpView;
    }

    boolean isViewAttach(){
       return mvpView!=null;
    }
}

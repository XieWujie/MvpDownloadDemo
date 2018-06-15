package com.example.xiewujie.mvpPicturedemo.mvpPresenter;

import android.content.Context;
import android.graphics.Bitmap;
import com.example.xiewujie.mvpPicturedemo.mvpModel.CallBackListener;
import com.example.xiewujie.mvpPicturedemo.mvpModel.ImageLoader;
import com.example.xiewujie.mvpPicturedemo.view.MyMvpView;

public class MyPresenter extends BasePresenter<MyMvpView> {

    private ImageLoader imageLoader;
    public MyPresenter(){

    }

    @Override
    public void attachView(MyMvpView mvpView,Context context) {
        super.attachView(mvpView,context);
        imageLoader = ImageLoader.with(mContext);
    }

    public void getData(String url){
        if (!isViewAttach())return;
        mvpView.showLoading();
        imageLoader.load(url, new CallBackListener<Bitmap,String>() {
            @Override
            public void onSucceed(Bitmap data) {
                if (isViewAttach()) {
                    mvpView.setData(data);
                    mvpView.hideLoading();
                }
            }

            @Override
            public void onFail(String msg) {
                if (isViewAttach()) {
                    mvpView.onFail(msg);
                    mvpView.hideLoading();
                }
            }
        });
    }
}

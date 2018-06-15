package com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure;

import android.content.Context;
import android.graphics.Bitmap;

public class DoubleCache implements Cache {
    private ImageLruCache mImageLruCache ;
    private ImageDiscache mImageDisCache;
    private DoubleCache(){

    }

    public DoubleCache with(Context context){
        mImageLruCache = ImageLruCache.getInstance();
        mImageDisCache = ImageDiscache.getInstance().with(context);
        return this;
    }
    public static DoubleCache getInstance(){
        return DoubleCaCheHelper.DOUBLE_CACHE;
    }
    static class DoubleCaCheHelper{
        static final DoubleCache DOUBLE_CACHE = new DoubleCache();
    }
    @Override
    public void putImage(String url, Bitmap bitmap) {
        mImageLruCache.putImage(url,bitmap);
        mImageDisCache.putImage(url,bitmap);
    }

    @Override
    public Bitmap getImage(String url) {
        return mImageLruCache.getImage(url)==null?mImageDisCache.getImage(url):mImageLruCache.getImage(url);
    }
}

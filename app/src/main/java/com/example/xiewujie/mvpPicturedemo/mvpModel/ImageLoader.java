package com.example.xiewujie.mvpPicturedemo.mvpModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure.Cache;
import com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure.DoubleCache;
import com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure.ImageDiscache;
import com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure.ImageLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class ImageLoader {
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1; //线程池的核心线程数
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1; //线程池所能容纳的最大线程数
    public static final ExecutorService EXECUTORS =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    private static Cache imageCache ;
    private String errorUrl;
    private static ImageLoader imageLoader = new ImageLoader();
    public static final int IMAGE_LRUCACHE = 0;
    public static final int IMAGE_DISCACHE = 1;

    //单例模式
    private ImageLoader(){

    }
    public static ImageLoader with(Context context){
        imageCache = DoubleCache.getInstance().with(context);
        return imageLoader;
    }

    public static ImageLoader setCacheType(int type,Context context){
        if (type==IMAGE_LRUCACHE){
            imageCache = ImageLruCache.getInstance();
        }else if (type==IMAGE_DISCACHE){
            imageCache = ImageDiscache.getInstance().with(context);
        }else {
            imageCache = DoubleCache.getInstance().with(context);
        }
        return imageLoader;
    }

    public ImageLoader load(String url,CallBackListener listener) {
        if (imageCache.getImage(url)!=null) {
            final Bitmap bitmap = imageCache.getImage(url);
           listener.onSucceed(bitmap);
        }else {
            loadImageFromUrl(url,listener);
        }
        return imageLoader;
    }

    public  ImageLoader error(String url){
        errorUrl = url;
        return this;
    }

    private void loadImageFromUrl(final String urlAddress, final CallBackListener listener){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream in = null;
                try {
                    URL url = new URL(urlAddress);
                    connection =(HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    in = connection.getInputStream();
                    Bitmap bitmap = CondenseImage.decodeBitmapFromInputStream(in,200,200);
                    if (bitmap==null||in==null){
                        listener.onFail(urlAddress);
                        return;
                    }
                    listener.onSucceed(bitmap);
                    imageCache.putImage(urlAddress,bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (connection!=null)
                            connection.disconnect();
                        if (in!=null)
                            in.close();

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        EXECUTORS.submit(runnable); //添加线程到线程池中
    }
}
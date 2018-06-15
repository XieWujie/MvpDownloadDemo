package com.example.xiewujie.mvpPicturedemo.mvpModel.dataResoure;

import android.graphics.Bitmap;

public interface Cache {
    void putImage(String url, Bitmap bitmap);
    Bitmap getImage(String url);
}

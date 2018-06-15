package com.example.xiewujie.mvpPicturedemo.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewAdapter extends PagerAdapter {

    private List<View> mList;

    public ViewAdapter(List<View> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       container.addView(mList.get(position));
       return mList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}

package com.example.xiewujie.mvpPicturedemo;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.xiewujie.mvpPicturedemo.adapter.ViewAdapter;
import com.example.xiewujie.mvpPicturedemo.mvpPresenter.MyPresenter;
import com.example.xiewujie.mvpPicturedemo.view.BaseActivity;
import com.example.xiewujie.mvpPicturedemo.view.MyMvpView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MyMvpView<Bitmap,String> {
    private MyPresenter presenter;
    private EditText inputText;
    private Button commitButtton;
    private ViewPager viewPager;
    private List<View> mList;
    private ViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.input_text);
        commitButtton = (Button) findViewById(R.id.commit_bt);
        viewPager = (ViewPager) findViewById(R.id.image_from_url);
        presenter = new MyPresenter();
        presenter.attachView(this, this);
        mList = new ArrayList<>();
        adapter = new ViewAdapter(mList);
        viewPager.setAdapter(adapter);
        commitButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(inputText.getText().toString());
                inputText.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    @Override
    public void setData(Bitmap data) {
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(data);
        mList.add(imageView);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(mList.size());
    }
}

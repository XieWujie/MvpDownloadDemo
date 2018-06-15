package com.example.xiewujie.mvpPicturedemo.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.xiewujie.mvpPicturedemo.R;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }
}

package com.booboomx.hotvideo.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.booboomx.hotvideo.R;

 /*仿咸鱼首页的界面*/
public class XianYuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xian_yu);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_xian_yu);
    }
}

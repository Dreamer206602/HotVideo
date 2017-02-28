package com.booboomx.hotvideo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * Created by booboomx on 17/2/28.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{


    protected Unbinder mUnBinder;
    private T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

package com.booboomx.hotvideo.ui.activity;

import android.os.Bundle;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mUnBinder= ButterKnife.bind(this);





    }
}

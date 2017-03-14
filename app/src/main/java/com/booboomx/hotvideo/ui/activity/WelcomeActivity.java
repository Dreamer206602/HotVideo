package com.booboomx.hotvideo.ui.activity;

import android.os.Bundle;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseActivity;
import com.booboomx.hotvideo.presenter.WelcomePresenter;
import com.booboomx.hotvideo.ui.view.WelcomeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.welcome_view)
    WelcomeView mWelcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mUnBinder= ButterKnife.bind(this);
        mPresenter=new WelcomePresenter(mWelcomeView);

    }
}

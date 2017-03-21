package com.booboomx.hotvideo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.widget.path.WowSplashView;
import com.booboomx.hotvideo.widget.path.WowView;

/**
 * 欢迎页
 */
public class Welcome2Activity extends AppCompatActivity {

    private WowSplashView mWowSplashView;
    private WowView mWowView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);


        mWowSplashView = (WowSplashView) findViewById(R.id.wowSplashView);
        mWowView = (WowView) findViewById(R.id.wowView);


        mWowSplashView.startAnimate();
        mWowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {

                mWowSplashView.setVisibility(View.GONE);
                mWowView.setVisibility(View.VISIBLE);

                mWowView.startAnimate(mWowSplashView.getDrawingCache());

                JumpUtil.go2MainActivity(Welcome2Activity.this);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });


    }
}

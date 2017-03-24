package com.booboomx.hotvideo.base;

/**
 * Created by booboomx on 17/3/21.
 */


import android.os.Bundle;
import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.widget.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * 1、想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 *2、设置activity的主题为android:theme="@style/CustomTransparent
 */
public  abstract class SwipeBackActivity extends BaseActivity {
    protected SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base, null);
        layout.attachToActivity(this);


        mUnBinder= ButterKnife.bind(this);
        onBaseCreate();

    }


    protected abstract int setLayoutResourceID();
    protected abstract void onBaseCreate();



}


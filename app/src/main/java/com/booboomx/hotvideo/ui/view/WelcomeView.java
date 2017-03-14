package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.presenter.contract.WelcomeContract;
import com.booboomx.hotvideo.ui.activity.WelcomeActivity;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.StringUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by booboomx on 17/3/14.
 */

public class WelcomeView extends RootView<WelcomeContract.Presenter> implements WelcomeContract.View {
    @BindView(R.id.iv_welcome)
    ImageView mIvWelcome;

    public WelcomeView(Context context) {
        super(context);
    }

    public WelcomeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        mPresenter= Preconditions.checkNotNull(presenter);


    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(List<String> list) {
        if (list != null) {

            int page = StringUtils.getRandomNumber(0, list.size() - 1);

            ImageLoader.load(mContext,list.get(page),mIvWelcome);
            mIvWelcome.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();


        }

    }

    @Override
    public void jumpToMain() {
        JumpUtil.go2MainActivity(mContext);
        ((WelcomeActivity)mContext).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);



    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.activity_welcome_view,this);


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }
}

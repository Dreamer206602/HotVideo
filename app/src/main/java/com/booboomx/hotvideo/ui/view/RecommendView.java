package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.contract.RecommendContract;

/**
 * Created by booboomx on 17/3/19.
 */

public class RecommendView extends RootView<RecommendContract.Presenter>implements RecommendContract.View {


    public RecommendView(Context context) {
        super(context);
    }

    public RecommendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showContent(VideoRes videoRes) {

    }

    @Override
    public void refreshFaile(String msg) {

    }

    @Override
    public void stopBanner(boolean stop) {

    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.fragment_recommend_view,this);


    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }
}

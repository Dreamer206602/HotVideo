package com.booboomx.hotvideo.ui.activity;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.presenter.WelfarePresenter;
import com.booboomx.hotvideo.ui.view.WelfareView;

import butterknife.BindView;

/**
 * 福利的界面
 */
public class WelfareActivity extends SwipeBackActivity {


    @BindView(R.id.welfare_view)
    WelfareView mView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void onBaseCreate() {

        mPresenter=new WelfarePresenter(mView);

    }
}

package com.booboomx.hotvideo.ui.activity;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.presenter.CollectionPresenter;
import com.booboomx.hotvideo.ui.view.CollectionView;

import butterknife.BindView;

public class CollectionActivity extends SwipeBackActivity {

    @BindView(R.id.collectView)
    CollectionView mView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_collection;
    }

    @Override
    protected void onBaseCreate() {

        mPresenter=new CollectionPresenter(mView,0);

    }
}

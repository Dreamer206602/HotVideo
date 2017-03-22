package com.booboomx.hotvideo.ui.fragment;


import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.presenter.DiscoverPresenter;
import com.booboomx.hotvideo.ui.view.DiscoverView;

import butterknife.BindView;

/**
 * 发现的界面
 */
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.three_view)
    DiscoverView mView;


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);

        mPresenter=new DiscoverPresenter(mView);
    }


    @Override
    public void lazyFetchData() {
        super.lazyFetchData();
        ((DiscoverPresenter)mPresenter).getData();
    }


}

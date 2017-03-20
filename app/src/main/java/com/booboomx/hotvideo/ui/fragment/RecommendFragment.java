package com.booboomx.hotvideo.ui.fragment;


import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.presenter.contract.RecommendPresenter;
import com.booboomx.hotvideo.ui.view.RecommendView;

import butterknife.BindView;

/**
 * 首页精选的界面
 */
public class RecommendFragment extends BaseFragment {

    @BindView(R.id.one_view)
    RecommendView mRecommendView;


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_recommend;
    }


    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter=new RecommendPresenter(mRecommendView);

    }

    @Override
    public void lazyFetchData() {
        super.lazyFetchData();
        ( (RecommendPresenter)mPresenter).onRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();
        mRecommendView.stopBanner(false);
    }


    @Override
    public void onPause() {
        super.onPause();
        mRecommendView.stopBanner(true);
    }
}

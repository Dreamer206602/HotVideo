package com.booboomx.hotvideo.ui.fragment;


import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.presenter.MinePresenter;
import com.booboomx.hotvideo.ui.view.MineView;

import butterknife.BindView;

/**
 * 我的界面
 */
public class MineFragment extends BaseFragment {

    public static final String SET_THEME = "SET_THEME";
    @BindView(R.id.mine_view)
    MineView mView;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
    }

    @Override
    public void lazyFetchData() {
        super.lazyFetchData();
        mPresenter=new MinePresenter(mView);
    }
}

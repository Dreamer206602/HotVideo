package com.booboomx.hotvideo.ui.fragment;


import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.presenter.ClassificationPresenter;
import com.booboomx.hotvideo.ui.view.ClassificationView;

import butterknife.BindView;

/**
 * 专题的界面
 */
public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.two_view)
    ClassificationView mView;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_classification;
    }


    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);

        mPresenter=new ClassificationPresenter(mView);
    }

    @Override
    public void lazyFetchData() {
        super.lazyFetchData();
        ((ClassificationPresenter)mPresenter).refresh();
    }
}

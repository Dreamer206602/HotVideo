package com.booboomx.hotvideo.ui.fragment;


import android.view.LayoutInflater;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.presenter.CommentPresenter;
import com.booboomx.hotvideo.ui.view.CommentView;

import butterknife.BindView;

/**
 *评论的界面
 */
public class VideoCommentFragment extends BaseFragment {

    @BindView(R.id.commentView)
    CommentView mCommentView;

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter=new CommentPresenter(mCommentView);
    }

    @Override
    public void lazyFetchData() {
        super.lazyFetchData();
        ((CommentPresenter)mPresenter).onRefresh();
    }
}

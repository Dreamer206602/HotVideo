package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.VideoInfoPresenter;
import com.booboomx.hotvideo.presenter.contract.CommentContract;
import com.booboomx.hotvideo.ui.adapter.CommentAdapter;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;

/**
 * Created by booboomx on 17/3/21.
 */

public class CommentView extends RootView<CommentContract.Presenter>implements CommentContract.View,RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    private CommentAdapter mAdapter;

    private TextView tvEmpty;

    public CommentView(Context context) {
        super(context);
    }

    public CommentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setPresenter(CommentContract.Presenter presenter) {

        mPresenter= Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

        EventUtil.showToast(mContext,msg);


    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void refreshFail(String msg) {

        if(!TextUtils.isEmpty(msg)){
            showError(msg);

            mRecyclerView.showError();
        }

    }

    @Override
    public void showContent(List<VideoType> videoTypes) {

        mAdapter.clear();
        if(videoTypes!=null&& videoTypes.size()<30){

            clearFooter();


        }
        mAdapter.addAll(videoTypes);

    }

    private void clearFooter() {

        mAdapter.setMore(new View(mContext),this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));
    }

    @Override
    public void showMoreContent(List<VideoType> list) {

        mAdapter.addAll(list);


    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.fragment_comment_view,this);
    }

    @Override
    protected void initView() {


        mRecyclerView.setAdapterWithProgress(mAdapter=new CommentAdapter(mContext));
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more,this);
        mAdapter.setNoMore(R.layout.view_no_more);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        SpaceDecoration spaceDecoration=new SpaceDecoration(ScreenUtil.dip2px(getContext(),8));

        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingHeaderFooter(false);

        mRecyclerView.addItemDecoration(spaceDecoration);

        tvEmpty= (TextView) mRecyclerView.getEmptyView();
        tvEmpty.setText("暂无评论");





    }

    @Override
    protected void initEvent() {

        mRecyclerView.setRefreshListener(this);
        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });

    }

    @Override
    public void onLoadMore() {

        mPresenter.loadMore();

    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();

    }


    @Subscriber(tag = VideoInfoPresenter.Put_DataId)
    public void  setData(String dataId){
        mPresenter.setMediaId(dataId);
        mPresenter.onRefresh();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDetachedFromWindow() {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromWindow();
    }
}

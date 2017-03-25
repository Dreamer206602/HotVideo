package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.gank.GankItemBean;
import com.booboomx.hotvideo.presenter.WelfarePresenter;
import com.booboomx.hotvideo.presenter.contract.WelfareContract;
import com.booboomx.hotvideo.ui.activity.WelfareActivity;
import com.booboomx.hotvideo.ui.adapter.WelfareAdapter;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by booboomx on 17/3/25.
 */

public class WelfareView extends RootView<WelfareContract.Presenter> implements WelfareContract.View,WelfareAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    WelfareAdapter mAdapter;

    public WelfareView(Context context) {
        super(context);
    }

    public WelfareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(WelfareContract.Presenter presenter) {
        mPresenter= Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

        EventUtil.showToast(getContext(),msg);


    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void refreshFail(String msg) {
        if(!TextUtils.isEmpty(msg))
            showError(msg);

        mRecyclerView.showError();


    }

    @Override
    public void loadMoreFail(String msg) {
        if(!TextUtils.isEmpty(msg))
            showError(msg);
        mAdapter.pauseMore();

    }

    @Override
    public void showContent(List<GankItemBean> list) {

        mAdapter.clear();
        if (list != null&&list.size()< WelfarePresenter.NUM_OF_PAGE) {

            clearFooter();

        }

        mAdapter.addAll(list);


    }

    private void clearFooter() {

        mAdapter.setMore(new View(mContext), this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));


    }

    @Override
    public void loadMoreContent(List<GankItemBean> list) {
        mAdapter.addAll(list);

    }

    @Override
    protected void getLayout() {

        inflate(getContext(), R.layout.activity_welfare_view,this);

    }

    @Override
    protected void initView() {

        titleName.setText(R.string.welfare);
        mRecyclerView.setAdapterWithProgress(mAdapter=new WelfareAdapter(getContext()));


        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more,this);

        mAdapter.setNoMore(R.layout.view_no_more);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


        SpaceDecoration spaceDecoration=new SpaceDecoration(ScreenUtil.dip2px(getContext(),8));
        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingHeaderFooter(false);

        mRecyclerView.addItemDecoration(spaceDecoration);



    }



    @Override
    protected void initEvent() {

        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });


        mAdapter.setError(R.layout.view_error)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.resumeMore();
                    }
                });

        mRecyclerView.getErrorView().setOnClickListener(new OnClickListener() {
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


    @OnClick(R.id.rl_back)
    public void onClick() {
        if (mContext instanceof WelfareActivity) {
            ((WelfareActivity) mContext).finish();
        }
    }
}

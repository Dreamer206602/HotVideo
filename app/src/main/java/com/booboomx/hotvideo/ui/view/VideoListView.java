package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.contract.VideoListContract;
import com.booboomx.hotvideo.ui.activity.VideoListActivity;
import com.booboomx.hotvideo.ui.adapter.VideoListAdapter;
import com.booboomx.hotvideo.utils.BeanUtil;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.JumpUtil;
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
 * Created by booboomx on 17/3/22.
 */

public class VideoListView extends RootView<VideoListContract.Presenter> implements VideoListContract.View, VideoListAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    @BindView(R.id.rl_back)
    RelativeLayout mRlBack;
    @BindView(R.id.title_name)
    ColorTextView mTitleName;

    private VideoListAdapter mAdapter;

    private VideoInfo mVideoInfo;
    private int pageSize = 30;

    public VideoListView(Context context) {
        super(context);
    }

    public VideoListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(VideoListContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(getContext(), msg);

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showTitle(String title) {


        mTitleName.setText(title);

    }

    @Override
    public void refreshFail(String msg) {

        if (!TextUtils.isEmpty(msg)) {
            showError(msg);
        }

        mRecyclerView.showError();
    }

    @Override
    public void loadMoreFail(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showError(msg);
        }

        mAdapter.pauseMore();
    }

    @Override
    public void showContent(List<VideoType> list) {

        mAdapter.clear();
        if (list != null && list.size() < pageSize) {
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
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);

    }

    @Override
    protected void getLayout() {
        inflate(getContext(), R.layout.activity_video_list_view, this);

    }

    @Override
    protected void initView() {

        mRecyclerView.setAdapterWithProgress(mAdapter = new VideoListAdapter(getContext()));

        mRecyclerView.setErrorView(R.layout.view_error);

        mAdapter.setMore(R.layout.view_more, this);

        mAdapter.setNoMore(R.layout.view_no_more);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        manager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));

        mRecyclerView.setLayoutManager(manager);

        SpaceDecoration spaceDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        spaceDecoration.setPaddingHeaderFooter(false);
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingEdgeSide(true);

        mRecyclerView.addItemDecoration(spaceDecoration);


    }

    @Override
    protected void initEvent() {


        mTitleName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EventUtil.isFastDoubleClick()) {
                    mRecyclerView.scrollToPosition(0);
                }

            }
        });

        mRecyclerView.setRefreshListener(this);


        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                mVideoInfo = BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position), mVideoInfo);
                JumpUtil.go2VideoInfoActivity(getContext(), mVideoInfo);

            }
        });

        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
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


    @OnClick(R.id.rl_back)
    public void back() {
        if (mContext instanceof VideoListActivity) {
            ((VideoListActivity) mContext).finish();
        }
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();

    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();


    }


    public void setTitleName(String title) {
        mTitleName.setText(title);
    }

}


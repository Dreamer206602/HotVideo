package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.app.Constants;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.contract.DiscoverContract;
import com.booboomx.hotvideo.ui.adapter.SwipeDeckAdapter;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.PreUtils;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.booboomx.hotvideo.widget.LVGhost;
import com.booboomx.hotvideo.widget.SwipeDeck;
import com.daprlabs.cardstack.SwipeFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by booboomx on 17/3/22.
 */

public class DiscoverView extends RootView<DiscoverContract.Presenter> implements DiscoverContract.View {


    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.swipe_deck)
    SwipeDeck swipeDeck;
    @BindView(R.id.swipeLayout)
    SwipeFrameLayout swipeLayout;
    @BindView(R.id.loading)
    LVGhost loading;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    private SwipeDeckAdapter mAdapter;
    private List<VideoType> videos = new ArrayList<>();


    public DiscoverView(Context context) {
        super(context);
    }

    public DiscoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(DiscoverContract.Presenter presenter) {

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
    public void showContent(VideoRes videoRes) {
        if (videoRes != null) {
            videos.clear();
            videos.addAll(videoRes.list);
            swipeDeck.removeAllViews();
            swipeDeck.removeAllViews();
            mAdapter = new SwipeDeckAdapter(videos, getContext());
            swipeDeck.setAdapter(mAdapter);
            tvNomore.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void refreshFail(String msg) {

        hideLoading();
        if (!TextUtils.isEmpty(msg)) {
            showError(msg);
        }


    }

    @Override
    public void hideLoading() {

        loading.setVisibility(View.GONE);


    }

    @Override
    public int getLastPage() {
        return PreUtils.getInt(getContext(), Constants.DISCOVERlASTpAGE, 1);
    }

    @Override
    public void setLasePage(int page) {

        PreUtils.putInt(getContext(), Constants.DISCOVERlASTpAGE, page);

    }

    @Override
    protected void getLayout() {
        inflate(getContext(), R.layout.fragment_discover_view, this);

    }

    @Override
    protected void initView() {

        titleName.setText("发现");
        ViewGroup.LayoutParams layoutParams =
                swipeDeck.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenHeight(getContext()) / 3 * 2;
        swipeDeck.setLayoutParams(layoutParams);
        swipeDeck.setHardwareAccelerationEnabled(true);


    }

    @Override
    protected void initEvent() {

        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

            }

            @Override
            public void cardSwipedRight(int position) {

            }

            @Override
            public void cardsDepleted() {

                swipeDeck.setVisibility(View.GONE);
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });

    }


    @OnClick({R.id.btn_next, R.id.tv_nomore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
            case R.id.tv_nomore:
                nextVideo();
                break;
        }
    }

    private void nextVideo() {

        swipeDeck.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        tvNomore.setVisibility(View.GONE);
        mPresenter.getData();

    }
}

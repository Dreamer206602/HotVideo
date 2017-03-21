package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.contract.RecommendContract;
import com.booboomx.hotvideo.ui.activity.MainActivity;
import com.booboomx.hotvideo.ui.adapter.BannerAdapter;
import com.booboomx.hotvideo.ui.adapter.RecommendAdapter;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.booboomx.hotvideo.widget.theme.ColorRelativeLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by booboomx on 17/3/19.
 */

public class RecommendView extends RootView<RecommendContract.Presenter> implements RecommendContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    @Nullable
    @BindView(R.id.title)
    ColorRelativeLayout title;

    @BindView(R.id.title_name)
    ColorTextView titleName;

    private RollPagerView banner;
    private View headView;

    TextView etSearchKey;
    RelativeLayout rlGoSearch;
    List<VideoInfo> recommend;

    private RecommendAdapter mAdapter;


    public RecommendView(Context context) {
        super(context);
    }

    public RecommendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void initView() {

        title.setVisibility(View.GONE);
        titleName.setText("精选");
        headView = LayoutInflater.from(mContext).inflate(R.layout.recommend_header, null);

        banner = ButterKnife.findById(headView, R.id.banner);
        rlGoSearch = ButterKnife.findById(headView, R.id.rlGoSearch);
        etSearchKey = ButterKnife.findById(headView, R.id.etSearchKey);
        banner.setPlayDelay(2000);


        mRecyclerView.setAdapterWithProgress(mAdapter = new RecommendAdapter(getContext()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setErrorView(R.layout.view_error);

        SpaceDecoration spaceDecoration = new SpaceDecoration(ScreenUtil.dip2px(getContext(), 8));
        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(spaceDecoration);


    }

    @Override
    protected void initEvent() {

        title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EventUtil.isFastDoubleClick()) {

                    mRecyclerView.scrollToPosition(0);
                }

            }
        });


        mRecyclerView.setRefreshListener(this);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (getHeaderScroll() <= ScreenUtil.dip2px(mContext, 150)) {

                    Subscription subscription =
                            Observable.timer(300, TimeUnit.MICROSECONDS)
                                    .compose(RxUtils.<Long>rxSchedulerHelper())
                                    .subscribe(new Action1<Long>() {
                                        @Override
                                        public void call(Long aLong) {

                                            float percentage = (float) getHeaderScroll() / ScreenUtil.dip2px(mContext, 150);

                                            title.setAlpha(percentage);

                                            if (percentage > 0) {
                                                title.setVisibility(VISIBLE);
                                            } else {
                                                title.setVisibility(GONE);
                                            }


                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {

                                        }
                                    });


                } else {
                    title.setAlpha(1.0f);
                    title.setVisibility(VISIBLE);

                }

            }
        });


        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                JumpUtil.go2VideoInfoActivity(mContext,mAdapter.getItem(position));


            }
        });


        mRecyclerView.getErrorView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });


        rlGoSearch.setOnClickListener(this);


    }

    public int getHeaderScroll() {
        if (headView == null) {
            return 0;
        }

        int top = headView.getTop();
        int abs = Math.abs(top);

        return abs;
    }


    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {

        mPresenter = Preconditions.checkNotNull(presenter);


    }

    @Override
    public void showError(String msg) {

        EventUtil.showToast(mContext, msg);

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void showContent(final VideoRes videoRes) {

        if (videoRes != null) {
            mAdapter.clear();
            List<VideoInfo> videoInfos;
            for (int i = 0; i < videoRes.list.size(); i++) {

                if (videoRes.list.get(i).title.equals("精彩推荐")) {
                    videoInfos = videoRes.list.get(i).childList;
                    mAdapter.addAll(videoInfos);
                    break;
                }

            }


            for (int i = 0; i < videoRes.list.size(); i++) {

                if (videoRes.list.get(i).title.equals("免费推荐")) {
                    recommend = videoRes.list.get(i).childList;

                    break;
                }

            }


            if(mAdapter.getHeaderCount()==0){

                mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
                    @Override
                    public View onCreateView(ViewGroup parent) {

                        banner.setHintView(new IconHintView(getContext(),
                                R.mipmap.ic_page_indicator_focused,
                                R.mipmap.ic_page_indicator,
                                ScreenUtil.dip2px(getContext(),10)));
                        banner.setHintPadding(0,0,0,ScreenUtil.dip2px(getContext(),8));
                        banner.setAdapter(new BannerAdapter(getContext(),videoRes.list.get(0).childList));


                        return headView;
                    }

                    @Override
                    public void onBindView(View headerView) {

                    }
                });

            }



        }


    }

    @Override
    public void refreshFaile(String msg) {


        if(!TextUtils.isEmpty(msg)){
            showError(msg);
            mRecyclerView.showError();
        }


    }

    @Subscriber(tag = MainActivity.Banner_Stop)
    @Override
    public void stopBanner(boolean stop) {
        if(stop){
            banner.pause();
        }else{
            banner.resume();
        }

    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.fragment_recommend_view, this);


    }


    @Override
    public void onRefresh() {

        mPresenter.onRefresh();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.rlGoSearch:


                break;
        }

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

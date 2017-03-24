package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.contract.VideoInfoContract;
import com.booboomx.hotvideo.ui.activity.VideoInfoActivity;
import com.booboomx.hotvideo.ui.fragment.VideoCommentFragment;
import com.booboomx.hotvideo.ui.fragment.VideoIntroFragment;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.booboomx.hotvideo.widget.LVGhost;
import com.booboomx.hotvideo.widget.SwipeViewPager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by booboomx on 17/3/21.
 */

public class VideoInfoView extends RootView<VideoInfoContract.Presenter>implements VideoInfoContract.View {

    @BindView(R.id.iv_collect)
    ImageView mIvCollect;

    @BindView(R.id.videoplayer)
    JCVideoPlayerStandard mJCVideoPlayer;

    @BindView(R.id.title_name)
    ColorTextView mTitleName;

    @BindView(R.id.viewpagertab)
    SmartTabLayout mTabLayout;

    @BindView(R.id.viewpager)
    SwipeViewPager mViewPager;

    @BindView(R.id.circle_loading)
    LVGhost mLoading;
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;

    VideoRes mVideoRes;
    private Animation mAnimation;




    public VideoInfoView(Context context) {
        super(context);
    }

    public VideoInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(VideoInfoContract.Presenter presenter) {

        mPresenter= Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {

        EventUtil.showToast(getContext(),msg);

    }

    @Override
    public void showContent(VideoRes videoRes) {


        this.mVideoRes=videoRes;
        mTitleName.setText(videoRes.title);
        if(!TextUtils.isEmpty(videoRes.pic)){

            ImageLoader.load(mContext,videoRes.pic,mJCVideoPlayer.thumbImageView);

        }


        if(!TextUtils.isEmpty(videoRes.getVideoUrl())){
            mJCVideoPlayer.setUp(videoRes.getVideoUrl(),
                    JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    videoRes.title);
            mJCVideoPlayer.onClick(mJCVideoPlayer.thumbImageView);
        }


    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(GONE);

    }

    @Override
    public void collected() {
        mIvCollect.setBackgroundResource(R.mipmap.collection_select);

    }

    @Override
    public void disCollect() {
        mIvCollect.setBackgroundResource(R.mipmap.collection);



    }

    @Override
    protected void getLayout() {


        inflate(mContext, R.layout.activity_vide_info_view,this);


    }

    @Override
    protected void initView() {


        mAnimation= AnimationUtils.loadAnimation(mContext,R.anim.view_hand);

        rlCollect.setVisibility(VISIBLE);


        FragmentPagerItemAdapter adapter=new
                FragmentPagerItemAdapter(((VideoInfoActivity)mContext).getSupportFragmentManager(),
                FragmentPagerItems.with(mContext)
                .add(R.string.video_intro, VideoIntroFragment.class)
                .add(R.string.video_commtent, VideoCommentFragment.class)
                .create()
        );

        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);


        mJCVideoPlayer.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mJCVideoPlayer.backButton.setVisibility(GONE);
        mJCVideoPlayer.titleTextView.setVisibility(GONE);
        mJCVideoPlayer.tinyBackImageView.setVisibility(GONE);



    }

    @OnClick(R.id.rl_back)
    public void  back(){

        if (mContext instanceof VideoInfoActivity) {
            ((VideoInfoActivity) mContext).finish();
        }

    }

    @OnClick(R.id.rl_collect)
    public void onClick() {
        if (mVideoRes != null) {
            mIvCollect.startAnimation(mAnimation);
            mPresenter.collect();
        }
    }

    @Override
    protected void initEvent() {

    }
}

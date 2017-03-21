package com.booboomx.hotvideo.ui.activity;

import android.os.Bundle;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.presenter.VideoInfoPresenter;
import com.booboomx.hotvideo.ui.view.VideoInfoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoInfoActivity extends SwipeBackActivity {

    VideoInfo mVideoInfo;
    @BindView(R.id.view_info_view)
    VideoInfoView mVideoInfoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);
        mUnBinder= ButterKnife.bind(this);

        getIntentData();
        mPresenter=new VideoInfoPresenter(mVideoInfoView,mVideoInfo);
    }

    private void getIntentData() {

        mVideoInfo= (VideoInfo) getIntent().getSerializableExtra("videoInfo");

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if(JCVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();

    }
}

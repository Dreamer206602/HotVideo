package com.booboomx.hotvideo.ui.activity;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.presenter.VideoListPresenter;
import com.booboomx.hotvideo.ui.view.VideoListView;

import butterknife.BindView;

/**
 * 视频列表的界面
 */
public class VideoListActivity extends SwipeBackActivity {

    String mTitle = "";
    String mCatalogId = "";
    @BindView(R.id.video_list_view)
    VideoListView mView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void onBaseCreate() {

        getIntentData();

    }

    private void getIntentData() {

        mCatalogId=getIntent().getStringExtra("catalogId");
        mTitle=getIntent().getStringExtra("title");

        mPresenter=new VideoListPresenter(mView,mCatalogId);

        mView.setTitleName(mTitle);





    }


}

package com.booboomx.hotvideo.ui.activity;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.presenter.SearchVideoListPresenter;
import com.booboomx.hotvideo.ui.view.SearchVideoListView;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends SwipeBackActivity {

    @BindView(R.id.search_video_info_view)
    SearchVideoListView mView;


    private List<VideoInfo>mList;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void onBaseCreate() {
        mList= (List<VideoInfo>) getIntent().getSerializableExtra("recommend");
        mPresenter=new SearchVideoListPresenter(mView,mList);


    }
}

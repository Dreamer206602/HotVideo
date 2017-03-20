package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.contract.RecommendContract;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.booboomx.hotvideo.widget.theme.ColorRelativeLayout;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.rollviewpager.RollPagerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by booboomx on 17/3/19.
 */

public class RecommendView extends RootView<RecommendContract.Presenter> implements RecommendContract.View {

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
    List<VideoInfo>recommend;





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
        headView= LayoutInflater.from(mContext).inflate(R.layout.recommend_header,null);

        banner= ButterKnife.findById(headView,R.id.banner);
        rlGoSearch=ButterKnife.findById(headView,R.id.rlGoSearch);
        etSearchKey=ButterKnife.findById(headView,R.id.etSearchKey);
        banner.setPlayDelay(2000);










    }

    @Override
    protected void initEvent() {

    }




    @Override
    public void setPresenter(RecommendContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showContent(VideoRes videoRes) {

    }

    @Override
    public void refreshFaile(String msg) {

    }

    @Override
    public void stopBanner(boolean stop) {

    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.fragment_recommend_view, this);


    }


}

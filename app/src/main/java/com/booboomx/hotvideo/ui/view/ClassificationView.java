package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.contract.ClassificationContract;
import com.booboomx.hotvideo.ui.adapter.ClassificationAdapter;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by booboomx on 17/3/21.
 */

public class ClassificationView extends RootView<ClassificationContract.Presenter> implements ClassificationContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.title_name)
    ColorTextView titleName;

    public static final String TAG="ClassificationView";

    private ClassificationAdapter mAdapter;

    private List<VideoInfo>videoInfos=new ArrayList<>();



    public ClassificationView(Context context) {
        super(context);
    }

    public ClassificationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(ClassificationContract.Presenter presenter) {

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
    public void showContent(VideoRes videoRes) {

        Log.i(TAG, "showContent: --->"+videoRes.list.size());
        if(videoRes.list!=null&&videoRes.list.size()>0){

            mAdapter.clear();
            videoInfos.clear();
            for (int i = 1; i < videoRes.list.size(); i++) {

                if(!TextUtils.isEmpty(videoRes.list.get(i).moreURL)&&
                        !TextUtils.isEmpty(videoRes.list.get(i).title)){

                    VideoInfo videoInfo = videoRes.list.get(i).childList.get(0);
                    videoInfo.title= videoRes.list.get(i).title;

                    videoInfo.moreURL=videoRes.list.get(i).moreURL;

                    videoInfos.add(videoInfo);

                }


            }

            mAdapter.addAll(videoInfos);
        }


    }

    @Override
    public void refreshFail(String msg) {


        if(!TextUtils.isEmpty(msg)){
            showError(msg);
        }

        mRecyclerView.showError();



    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fragment_classification_view,this);

    }

    @Override
    protected void initView() {

        titleName.setText("专题");

        mRecyclerView.setAdapterWithProgress(mAdapter=new ClassificationAdapter(getContext()));

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        mRecyclerView.setErrorView(R.layout.view_error);

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

                JumpUtil.go2VideoListActivity(getContext(), StringUtils.getCatalogId(mAdapter.getItem(position).moreURL),mAdapter.getItem(position).title);

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
    public void onRefresh() {
        mPresenter.refresh();
    }
}

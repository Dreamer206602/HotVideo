package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.VideoInfoPresenter;
import com.booboomx.hotvideo.presenter.contract.CollectionContract;
import com.booboomx.hotvideo.ui.activity.CollectionActivity;
import com.booboomx.hotvideo.ui.activity.HistoryActivity;
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

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by booboomx on 17/3/24.
 */

public class CollectionView extends RootView<CollectionContract.Presenter> implements CollectionContract.View {

    @BindView(R.id.rl_collect_clear)
    RelativeLayout rlCollectClear;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;
    VideoInfo videoInfo;


    public CollectionView(Context context) {
        super(context);
    }

    public CollectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setPresenter(CollectionContract.Presenter presenter) {
        mPresenter= Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

        EventUtil.showToast(getContext(),msg);

    }

    @Override
    public boolean isActivie() {
        return mActive;
    }

    @Override
    public void showContent(List<VideoType> list) {
        if (list != null) {

            mAdapter.clear();
            mAdapter.addAll(list);
        }

    }

    @Override
    protected void getLayout() {

        inflate(getContext(), R.layout.activity_collection_view,this);

    }

    @Override
    protected void initView() {

        setTitle();

        rlCollectClear.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapterWithProgress(mAdapter=new VideoListAdapter(getContext()));
        GridLayoutManager manager=new GridLayoutManager(getContext(),3);

        manager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));

        mRecyclerView.setLayoutManager(manager);

        SpaceDecoration spaceDecoration=new SpaceDecoration(ScreenUtil.dip2px(getContext(),8));
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(spaceDecoration);


    }

    private void setTitle() {
        if(mContext instanceof CollectionActivity){
            titleName.setText("收藏");
        }else if(mContext instanceof HistoryActivity){
            titleName.setText("历史");
        }

    }

    @Override
    protected void initEvent() {

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                videoInfo= BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position),videoInfo);

                JumpUtil.go2VideoInfoActivity(getContext(),videoInfo);
            }
        });

    }


    @Subscriber(tag = VideoInfoPresenter.Refresh_Collection_List)
    public void setData(String tag) {
        mPresenter.getCollectData();
    }


    @OnClick({R.id.rl_back, R.id.rl_collect_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                if (mContext instanceof CollectionActivity) {
                    ((CollectionActivity) mContext).finish();
                } else if (mContext instanceof HistoryActivity) {
                    ((HistoryActivity) mContext).finish();
                }
                break;
            case R.id.rl_collect_clear:
                mAdapter.clear();
                mPresenter.delAllData();
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

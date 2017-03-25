package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.VideoInfoPresenter;
import com.booboomx.hotvideo.presenter.contract.MineContract;
import com.booboomx.hotvideo.ui.activity.CollectionActivity;
import com.booboomx.hotvideo.ui.activity.HistoryActivity;
import com.booboomx.hotvideo.ui.activity.SettingActivity;
import com.booboomx.hotvideo.ui.adapter.MineHistoryVideoListAdapter;
import com.booboomx.hotvideo.ui.fragment.MineFragment;
import com.booboomx.hotvideo.utils.BeanUtil;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.booboomx.hotvideo.R.id.recyclerView;

/**
 * Created by booboomx on 17/3/23.
 */

public class MineView extends RootView<MineContract.Presenter>implements MineContract.View {

    MineHistoryVideoListAdapter mAdapter;

    VideoInfo videoInfo;
    @BindView(R.id.title_name)
    ColorTextView titleName;
    @BindView(R.id.rl_them)
    RelativeLayout rlThem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(recyclerView)
    EasyRecyclerView mRecyclerView;
    @BindView(R.id.tv_history)
    TextView mTvHistory;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_them)
    TextView tvThem;

    public MineView(Context context) {
        super(context);
    }

    public MineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setPresenter(MineContract.Presenter presenter) {
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
    public void showContent(List<VideoType> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        if(list.size()>0){
            mRecyclerView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    protected void getLayout() {

        inflate(getContext(), R.layout.fragment_mine_view,this);

    }

    @Override
    protected void initView() {

        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        titleName.setText(R.string.mine);



        StringUtils.setIconDrawable(mContext, mTvHistory, MaterialDesignIconic.Icon.gmi_account_calendar, 16, 15);
        StringUtils.setIconDrawable(mContext, tvDown, MaterialDesignIconic.Icon.gmi_time_countdown, 16, 15);
        StringUtils.setIconDrawable(mContext, tvCollection, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 15);
        StringUtils.setIconDrawable(mContext, tvThem, MaterialDesignIconic.Icon.gmi_palette, 16, 15);



        mRecyclerView.setAdapterWithProgress(mAdapter=new MineHistoryVideoListAdapter(getContext()));

        GridLayoutManager manager=new GridLayoutManager(getContext(),3);
        manager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));

        mRecyclerView.setLayoutManager(manager);

        SpaceDecoration spaceDecoration=new SpaceDecoration(ScreenUtil.dip2px(getContext(),8));

        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingHeaderFooter(false);

        mRecyclerView.addItemDecoration(spaceDecoration);



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


    @Subscriber(tag = VideoInfoPresenter.Refresh_History_List)
    public void setData(String tag) {
        mPresenter.getHistoryData();
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

    @OnClick({R.id.rl_record, R.id.rl_down, R.id.rl_collection, R.id.rl_them, R.id.img_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_record:
                getContext().startActivity(new Intent(mContext, HistoryActivity.class));
                break;
            case R.id.rl_down:
                EventUtil.showToast(getContext(), "敬请期待");
                break;
            case R.id.rl_collection:
                getContext().startActivity(new Intent(mContext, CollectionActivity.class));
                break;
            case R.id.rl_them:
                EventBus.getDefault().post("", MineFragment.SET_THEME);
                break;
            case R.id.img_setting:
                getContext().startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }
}

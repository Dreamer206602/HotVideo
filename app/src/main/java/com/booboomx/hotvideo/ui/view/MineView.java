package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.contract.MineContract;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.widget.ColorTextView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.simple.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

import static com.booboomx.hotvideo.R.id.recyclerView;

/**
 * Created by booboomx on 17/3/23.
 */

public class MineView extends RootView<MineContract.Presenter>implements MineContract.View {


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



    }

    @Override
    protected void initEvent() {

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

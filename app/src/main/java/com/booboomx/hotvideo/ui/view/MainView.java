package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.app.Constants;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.presenter.contract.MainContract;
import com.booboomx.hotvideo.ui.activity.CollectionActivity;
import com.booboomx.hotvideo.ui.activity.MainActivity;
import com.booboomx.hotvideo.ui.activity.SettingActivity;
import com.booboomx.hotvideo.ui.activity.WelfareActivity;
import com.booboomx.hotvideo.ui.adapter.MyFragmentPagerAdapter;
import com.booboomx.hotvideo.ui.fragment.ClassificationFragment;
import com.booboomx.hotvideo.ui.fragment.DiscoverFragment;
import com.booboomx.hotvideo.ui.fragment.MineFragment;
import com.booboomx.hotvideo.ui.fragment.RecommendFragment;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.PreUtils;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.utils.ThemeUtils;
import com.booboomx.hotvideo.widget.ResideLayout;
import com.booboomx.hotvideo.widget.UnScrollViewPager;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.views.PgyerDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;

import static com.booboomx.hotvideo.ui.activity.MainActivity.Banner_Stop;

/**
 * Created by booboomx on 17/3/16.
 */

public class MainView extends RootView<MainContract.Presenter> implements MainContract.View, RadioGroup.OnCheckedChangeListener {


    final int WAIT_TIME = 200;
    @BindView(R.id.tv_collection)
    TextView mTvCollect;

    @BindView(R.id.tv_downLoad)
    TextView mTvDownLoad;
    @BindView(R.id.tv_feedBack)
    TextView mTvFeedBack;
    @BindView(R.id.tv_goodSoft)
    TextView mTvGoodSoft;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.tv_share)
    TextView mTvShare;

    @BindView(R.id.tv_about)
    TextView mTvAbout;
    @BindView(R.id.tv_theme)
    TextView mTvTheme;

    @BindView(R.id.viewPager)
    UnScrollViewPager mViewPager;

    @BindView(R.id.resideLayout)
    ResideLayout mResideLayout;

    @BindView(R.id.tab_rg_menu)
    RadioGroup mRadioGroup;

    @BindView(R.id.tab_rb_1)
    RadioButton mRadioButton1;
    @BindView(R.id.tab_rb_2)
    RadioButton mRadioButton2;
    @BindView(R.id.tab_rb_3)
    RadioButton mRadioButton3;
    @BindView(R.id.tab_rb_4)
    RadioButton mRadioButton4;


    MainActivity mActivity;
    private MyFragmentPagerAdapter mAdapter;


    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new ClassificationFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new MineFragment());

        return fragments;

    }

    @Override
    protected void initView() {
        mActivity = (MainActivity) mContext;
        List<Fragment> fragments = initFragments();
        mViewPager.setScrollable(false);//禁止左右滑动

        mAdapter = new MyFragmentPagerAdapter(mActivity.getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(fragments.size());

        StringUtils.setIconDrawable(mContext, mTvCollect, MaterialDesignIconic.Icon.gmi_collection_bookmark, 16, 10);//收藏
        StringUtils.setIconDrawable(mContext, mTvDownLoad, MaterialDesignIconic.Icon.gmi_download, 16, 10);//下载
        StringUtils.setIconDrawable(mContext, mTvGoodSoft, MaterialDesignIconic.Icon.gmi_mood, 16, 10);//福利
        StringUtils.setIconDrawable(mContext, mTvShare, MaterialDesignIconic.Icon.gmi_share, 16, 10);//分享
        StringUtils.setIconDrawable(mContext, mTvFeedBack, MaterialDesignIconic.Icon.gmi_android, 16, 10);//反馈
        StringUtils.setIconDrawable(mContext, mTvSetting, MaterialDesignIconic.Icon.gmi_settings, 16, 10);//设置
        StringUtils.setIconDrawable(mContext, mTvAbout, MaterialDesignIconic.Icon.gmi_account, 16, 10);//
        StringUtils.setIconDrawable(mContext, mTvTheme, MaterialDesignIconic.Icon.gmi_palette, 16, 10);//


    }

    @Override
    protected void initEvent() {

        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mResideLayout.setPanelSlideListener(new ResideLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                postBannerState(true);
            }

            @Override
            public void onPanelOpened(View panel) {
                postBannerState(true);



            }

            @Override
            public void onPanelClosed(View panel) {
                postBannerState(false);


            }
        });


    }

    private void postBannerState(final boolean stop) {

        Subscription subscribe = Observable.timer(200, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new rx.Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        EventBus.getDefault().post(stop,Banner_Stop);

                    }
                });



    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.activity_main_view, this);

    }


    @OnClick({R.id.tv_collection, R.id.tv_downLoad, R.id.tv_goodSoft, R.id.tv_share, R.id.tv_feedBack, R.id.tv_setting, R.id.tv_about, R.id.tv_theme})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_collection:
                mContext.startActivity(new Intent(mContext, CollectionActivity.class));

                break;
            case R.id.tv_downLoad:
                EventUtil.showToast(mContext, "敬请期待");

                break;
            case R.id.tv_goodSoft:
                if(getResideLayout().isOpen()){
                    getResideLayout().closePane();
                }
                mContext.startActivity(new Intent(mContext, WelfareActivity.class));

                break;
            case R.id.tv_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.setting_recommend_content));
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
                break;
            case R.id.tv_feedBack:
                // 以对话框的形式弹出
                PgyerDialog.setDialogTitleBackgroundColor(PreUtils.getString(mContext, Constants.PRIMARYCOLOR, "#000000"));
                PgyerDialog.setDialogTitleTextColor(PreUtils.getString(mContext, Constants.TITLECOLOR, "#0aa485"));
                PgyFeedback.getInstance().showDialog(mContext).d().setChecked(false);
                break;
            case R.id.tv_setting:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));

                break;
            case R.id.tv_about:
                new MaterialDialog.Builder(mContext)
                        .title(R.string.about)
                        .titleColor(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                        .icon(new IconicsDrawable(mContext)
                                .color(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                                .icon(MaterialDesignIconic.Icon.gmi_account)
                                .sizeDp(20))
                        .content(R.string.about_me)
                        .contentColor(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.tv_theme:
                setTheme("");
                break;
        }

    }

    @Subscriber(tag = MineFragment.SET_THEME)
    public void setTheme(String content) {

        new ColorChooserDialog.Builder(mActivity, R.string.theme)
                .customColors(R.array.colors, null)
                .doneButton(R.string.done)
                .cancelButton(R.string.cancel)
                .allowUserColorInput(false)
                .allowUserColorInputAlpha(false)
                .show();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


        switch (checkedId) {
            case R.id.tab_rb_1:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_rb_2:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_rb_3:
                mViewPager.setCurrentItem(2, false);

                break;
            case R.id.tab_rb_4:
                mViewPager.setCurrentItem(3, false);

                break;
        }

    }


    public ResideLayout getResideLayout() {
        return mResideLayout;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}

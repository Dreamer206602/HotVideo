package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.presenter.contract.MainContract;
import com.booboomx.hotvideo.ui.activity.MainActivity;
import com.booboomx.hotvideo.ui.adapter.MyFragmentPagerAdapter;
import com.booboomx.hotvideo.ui.fragment.ClassificationFragment;
import com.booboomx.hotvideo.ui.fragment.DIscoverFragment;
import com.booboomx.hotvideo.ui.fragment.MineFragment;
import com.booboomx.hotvideo.ui.fragment.RecommendFragment;
import com.booboomx.hotvideo.widget.ResideLayout;
import com.booboomx.hotvideo.widget.UnScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        fragments.add(new DIscoverFragment());
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
        mViewPager.setOffscreenPageLimit(fragments.size());


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

            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });


    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void getLayout() {

        inflate(mContext, R.layout.activity_main_view, this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {


        switch (checkedId) {
            case R.id.tab_rb_1:
                mViewPager.setCurrentItem(0,false);
                break;
            case R.id.tab_rb_2:
                mViewPager.setCurrentItem(1,false);
                break;
            case R.id.tab_rb_3:
                mViewPager.setCurrentItem(2,false);

                break;
            case R.id.tab_rb_4:
                mViewPager.setCurrentItem(3,false);

                break;
        }

    }
}

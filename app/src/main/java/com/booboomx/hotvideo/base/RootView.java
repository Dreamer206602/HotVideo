package com.booboomx.hotvideo.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by booboomx on 17/3/12.
 */

public abstract class RootView<T extends BasePresenter>extends LinearLayout {

    protected  boolean mActive;//是否被销毁
    protected  Context mContext;
    private Unbinder mUnbinder;
    public   T mPresenter;



    public RootView(Context context) {
        super(context);
        init();
    }

    public RootView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RootView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract  void getLayout();

    protected abstract  void initView();

    protected abstract  void initEvent();



    private  void  init(){

        mContext=getContext();
        getLayout();
        mUnbinder= ButterKnife.bind(this);
        mActive=true;
        initView();
        initEvent();

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mActive=true;
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null) {
            mPresenter.detachView();
            mActive=false;
            mUnbinder.unbind();
            mContext=null;
        }
    }
}

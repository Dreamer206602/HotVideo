package com.booboomx.hotvideo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.booboomx.hotvideo.utils.KL;

/**
 * Created by booboomx on 17/3/2.
 */

public class UnScrollViewPager extends ViewPager {
    private boolean isScrollable=false;
    private Context mContext;

    public UnScrollViewPager(Context context) {
        super(context);
        this.mContext=context;
        Scroller scroller=new Scroller(mContext);
    }

    public UnScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isScrollable)
            return  super.onTouchEvent(ev);

        boolean b = super.onTouchEvent(ev);
        KL.d(UnScrollViewPager.class,"onTouchEvent"+b);

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isScrollable){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }
}

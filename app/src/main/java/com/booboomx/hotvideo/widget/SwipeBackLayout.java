package com.booboomx.hotvideo.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;


import java.util.LinkedList;
import java.util.List;

/**
 * Created by booboomx on 17/3/2.
 */

public class SwipeBackLayout extends FrameLayout{
    public static final String TAG=SwipeBackLayout.class.getSimpleName();


    private View mContentView;
    private int mTouchSlop;
    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int viewWidth;
    private boolean isSliding;
    private boolean isFinish;
    private Drawable mShadowDrawable;
    private Activity mActivity;
    private List<ViewPager>mViewPagers=new LinkedList<>();




    public SwipeBackLayout(@NonNull Context context) {
//        super(context);
        this(context,null);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);

    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller=new Scroller(context);
        mShadowDrawable=getResources().getDrawable(R.mipmap.shadow_left);

    }


    public void attachToActivity(Activity activity){

        mActivity=activity;
        TypedArray typedArray = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});

        int resourceId = typedArray.getResourceId(0, 0);
        typedArray.recycle();


        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        View childAt = decorView.getChildAt(0);
        childAt.setBackgroundResource(resourceId);
        decorView.removeView(childAt);
        addView(childAt);
        setContentView(childAt);
        decorView.addView(this);

    }

    private void setContentView(View decorChild){

        mContentView= (View) decorChild.getParent();

    }

    /**
     *  事件拦截的操作
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {

        ViewPager mViewPager = getTouchViewPager(mViewPagers, event);
        if(mViewPager!=null&&mViewPager.getCurrentItem()!=0){
            return super.onInterceptHoverEvent(event);

        }



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                 downX = (int) event.getRawX();
                 downY = (int) event.getRawY();
                break;




            case MotionEvent.ACTION_MOVE:

                int moveX = (int) event.getRawX();

                //满足此条件屏蔽 SlidingLayout里面子类的touch事件
                if(moveX-downX>mTouchSlop&&
                        Math.abs((int) event.getRawY()-downY)<mTouchSlop){
                    return true;
                }
                break;
        }

        return super.onInterceptHoverEvent(event);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = tempX - moveX;
                if(moveX-deltaX>mTouchSlop&&Math.abs((int)event.getRawY()-downY)<mTouchSlop){
                    isSliding=true;
                }

                if(moveX-downX>=0&&isSliding){
                    mContentView.scrollBy(deltaX,0);
                }
                break;

            case MotionEvent.ACTION_UP:
                isSliding=false;
                if(mContentView.getScaleX()<=-viewWidth/2){
                    isFinish=true;
                    //
                    scrollRight();
                }else{
                    scrollOrigin();
                    isFinish=false;
                }
                break;
        }
        return true;//表示事件进行消费
    }


    @Override
    public void computeScroll() {

        if(mScroller.computeScrollOffset()){
            mContentView.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();


            if(mScroller.isFinished()&&isFinish){
                mActivity.finish();
            }
        }
    }

    /**
     * 划出界面
     */
    private void scrollRight(){

        final  int delta= (int) (viewWidth+mContentView.getScaleX());


        mScroller.startScroll(mContentView.getScrollX(),0,-delta+1,0,Math.abs(delta));

        postInvalidate();





    }


    /**
     * 滑动起始位置
     */
    private void scrollOrigin() {

        int delta = (int) mContentView.getScaleX();

        mScroller.startScroll(mContentView.getScrollX(),0,-delta,0,Math.abs(delta));
        postInvalidate();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            viewWidth=this.getWidth();
            getALViewPager(mViewPagers,this);
            Log.i(TAG, "onLayout: Viewpager size=="+mViewPagers.size());
        }

    }

    private void getALViewPager(List<ViewPager> viewPagers, ViewGroup parent) {


        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childAt = parent.getChildAt(i);
            if(childAt instanceof ViewPager){
                mViewPagers.add((ViewPager) childAt);
            }else if(childAt instanceof ViewGroup){
                getALViewPager(mViewPagers, (ViewGroup) childAt);
            }

        }


    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(mShadowDrawable!=null&&mContentView!=null){
            int left = mContentView.getLeft() - mShadowDrawable.getIntrinsicWidth();


            int right=left+mShadowDrawable.getIntrinsicWidth();

            int top=mContentView.getTop();

            int bottom = mContentView.getBottom();

            mShadowDrawable.setBounds(left,top,right,bottom);
            mShadowDrawable.draw(canvas);

        }

    }

    /**
     * 返回touch的ViewPager
     * @param mViewPagers
     * @param ev
     * @return
     */
    private ViewPager getTouchViewPager(List<ViewPager>mViewPagers,MotionEvent ev){

        if(mViewPagers==null||mViewPagers.size()==0){
            return null;
        }

        Rect mRect=new Rect();
        for (ViewPager v:mViewPagers) {

            v.getHitRect(mRect);
            if(mRect.contains((int) ev.getX(),(int) ev.getY())){
                return v;
            }

        }
        return null;


    }
}

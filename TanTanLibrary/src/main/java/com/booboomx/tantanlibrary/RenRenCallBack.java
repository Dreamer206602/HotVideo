package com.booboomx.tantanlibrary;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by booboomx on 17/5/2.
 */

public class RenRenCallBack extends ItemTouchHelper.SimpleCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List mDatas;

    public RenRenCallBack( RecyclerView recyclerView, RecyclerView.Adapter adapter, List datas) {
       this(0,
               ItemTouchHelper.LEFT|ItemTouchHelper.UP|ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN,
               recyclerView,adapter,datas);
    }
    public RenRenCallBack(int dragDirs, int swipeDirs, RecyclerView recyclerView, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mDatas = datas;
    }

    //水平方向是否可以被回收掉的阈值
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        //2016 12 26 考虑 探探垂直上下方向滑动，不删除卡片，这里参照源码写死0.5f
        return mRecyclerView.getWidth() * /*getSwipeThreshold(viewHolder)*/ 0.5f;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        // 实现循环的要点
        Object remove = mDatas.remove(viewHolder.getAdapterPosition());
        mDatas.add(0,remove);
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        //人人影视的效果
        //if (isCurrentlyActive) {
        //先根据滑动的dxdy 算出现在动画的比例系数fraction

        double swipwValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipwValue / getThreshold(viewHolder);

        //边界修正 最大为1
        if(fraction>1){
            fraction=1;
        }

        //对每个childView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View child = recyclerView.getChildAt(i);
            //第几层,举例子，count =7， 最后一个TopView（6）是第0层，

            int level = childCount - i - 1;
            if(level>0){

                child.setScaleX((float) (1-CardConfig.SCALE_GAP*level+fraction*CardConfig.SCALE_GAP));


                if(level<CardConfig.MAX_SHOW_COUNT-1){

                    child.setScaleY((float) (1-CardConfig.SCALE_GAP*level+fraction*CardConfig.SCALE_GAP));
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP*level-fraction*CardConfig.TRANS_Y_GAP));

                }else{

                }
            }


        }


    }
}

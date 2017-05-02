package com.booboomx.cardswipelibrary;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by booboomx on 17/5/1.
 */

public class CardItemTouchHelperCallback<T> extends ItemTouchHelper.Callback {

    private  RecyclerView.Adapter mAdapter;
    private List<T>dataList;
    private OnSwipeListener<T>mListener;


    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList) {
        this.mAdapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
    }

    public CardItemTouchHelperCallback(@NonNull RecyclerView.Adapter adapter, @NonNull List<T> dataList, OnSwipeListener<T> listener) {
        this.mAdapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = listener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    public void setOnSwipedListener(OnSwipeListener<T> mListener) {
        this.mListener = mListener;
    }




    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        int dragFlags=0;
        int swipeFlags=0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if(layoutManager instanceof  CardLayoutManager){
            swipeFlags=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        }


        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }


    @Override
    public boolean isItemViewSwipeEnabled() {

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        //移除 OnTouchListener,否则触摸滑动会乱了
        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition = viewHolder.getLayoutPosition();

        T remove = dataList.remove(layoutPosition);
        mAdapter.notifyDataSetChanged();
        if (mListener != null) {
            mListener.onSwiped(viewHolder,remove,direction==ItemTouchHelper.LEFT?CardConfig.SWIPED_LEFT:CardConfig.SWIPED_RIGHT);
        }

        //没有数据的时候
        if(mAdapter.getItemCount()==0){
            if (mListener != null) {
                mListener.onSwipedClear();
            }
        }


    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


        View itemView = viewHolder.itemView;

        if(actionState==ItemTouchHelper.ACTION_STATE_SWIPE){

            float ratio = dX / getThreshold(recyclerView, viewHolder);

            // ratio (-1,1) 最大为1 或者－1
            if(ratio>1){
                ratio=1;
            }else if(ratio<-1){
                ratio=-1;
            }

            itemView.setRotation(ratio*CardConfig.DEFAULT_ROTATE_DEGREE);
            // 当数据源个数大于最大的显示个数时

            int childCount = recyclerView.getChildCount();
            if(childCount>CardConfig.DEFAULT_SHOW_ITEM){

                for (int position = 1; position <childCount-1 ; position++) {


                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);

                    view.setScaleX(1-index*CardConfig.DEFAULT_SCALE+Math.abs(ratio)*CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1-index*CardConfig.DEFAULT_SCALE+Math.abs(ratio)*CardConfig.DEFAULT_SCALE);

                    view.setTranslationY((index-Math.abs(ratio))*itemView.getMeasuredHeight()/CardConfig.DEFAULT_TRANSLATE_Y);

                }


            }else{

                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount-1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1-index*CardConfig.DEFAULT_SCALE+Math.abs(ratio)*CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1-index*CardConfig.DEFAULT_SCALE+Math.abs(ratio)*CardConfig.DEFAULT_SCALE);

                    view.setTranslationY((index-Math.abs(ratio))*itemView.getMeasuredHeight()/CardConfig.DEFAULT_TRANSLATE_Y);

                }

            }

            if (mListener != null) {
                if(ratio!=0){

                    mListener.onSwiping(viewHolder,ratio,ratio<0?CardConfig.SWIPING_LEFT:CardConfig.SWIPING_RIGHT);

                }else{

                    mListener.onSwiping(viewHolder,ratio,CardConfig.SWIPING_NONE);

                }
            }


        }


    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }


    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }




}

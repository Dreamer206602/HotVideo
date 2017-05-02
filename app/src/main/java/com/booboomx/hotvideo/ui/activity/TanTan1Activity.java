package com.booboomx.hotvideo.ui.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.booboomx.cardswipelibrary.CardConfig;
import com.booboomx.cardswipelibrary.CardItemTouchHelperCallback;
import com.booboomx.cardswipelibrary.CardLayoutManager;
import com.booboomx.cardswipelibrary.OnSwipeListener;
import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TanTan1Activity extends SwipeBackActivity {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<Integer> mList = new ArrayList<>();


    private void initView() {

        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        CardItemTouchHelperCallback callback=new CardItemTouchHelperCallback(mRecyclerView.getAdapter(),mList);

        callback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

                MyAdapter.MyViewHolder myViewHolder= (MyAdapter.MyViewHolder) viewHolder;

                myViewHolder.itemView.setAlpha(1-Math.abs(ratio)*0.2f);

                if(direction== CardConfig.SWIPING_LEFT){
                    myViewHolder.ivDisLike.setAlpha(Math.abs(ratio));
                }else if(direction==CardConfig.SWIPING_RIGHT){
                    myViewHolder.ivLike.setAlpha(Math.abs(ratio));
                }else{
                    myViewHolder.ivLike.setAlpha(0f);
                    myViewHolder.ivDisLike.setAlpha(0f);

                }

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {

                MyAdapter.MyViewHolder myViewHolder= (MyAdapter.MyViewHolder) viewHolder;
                myViewHolder.itemView.setAlpha(1f);
                myViewHolder.ivLike.setAlpha(0f);
                myViewHolder.ivDisLike.setAlpha(0f);


            }

            @Override
            public void onSwipedClear() {


                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        initData();
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                },3000L);

            }
        });


        ItemTouchHelper helper=new ItemTouchHelper(callback);
        CardLayoutManager manager=new CardLayoutManager(mRecyclerView,helper);
        mRecyclerView.setLayoutManager(manager);
        helper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_tan_tan1;
    }

    @Override
    protected void onBaseCreate() {

        initData();
        initView();

    }


    private void initData() {
        mList.add(R.drawable.img_avatar_01);
        mList.add(R.drawable.img_avatar_02);
        mList.add(R.drawable.img_avatar_03);
        mList.add(R.drawable.img_avatar_04);
        mList.add(R.drawable.img_avatar_05);
        mList.add(R.drawable.img_avatar_06);
        mList.add(R.drawable.img_avatar_07);
    }


    public class MyAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tantan, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

           MyViewHolder myholder= (MyViewHolder) holder;
            myholder.ivAvatar.setImageResource(mList.get(position));


        }

        @Override
        public int getItemCount() {
            return mList.size() > 0 ? mList.size() : 0;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.iv_avatar)
            ImageView ivAvatar;
            @BindView(R.id.iv_dislike)
            ImageView ivDisLike;
            @BindView(R.id.iv_like)
            ImageView ivLike;


            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}

package com.booboomx.hotvideo.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.tantanlibrary.CardConfig;
import com.booboomx.tantanlibrary.OverLayCardLayoutManager;
import com.booboomx.tantanlibrary.RenRenCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenRenActivity extends SwipeBackActivity {

    public static final String TAG=RenRenActivity.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private List<Integer>mDatas=new ArrayList<>();
    private MyAdapter mMyAdapter;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_ren_ren;
    }

    @Override
    protected void onBaseCreate() {
        initData();
        mRecyclerView.setLayoutManager(new OverLayCardLayoutManager());
        mMyAdapter=new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);

        CardConfig.init(this);
        ItemTouchHelper.Callback callback=new RenRenCallBack(mRecyclerView,mMyAdapter,mDatas);

        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

    }

    private void initData() {
        mDatas.add(R.drawable.pic1);
        mDatas.add(R.drawable.pic2);
        mDatas.add(R.drawable.pic3);
        mDatas.add(R.drawable.pic4);
        mDatas.add(R.drawable.pic5);
        mDatas.add(R.drawable.pic6);
        mDatas.add(R.drawable.pic6);
        mDatas.add(R.drawable.pic7);
        mDatas.add(R.drawable.pic8);
        mDatas.add(R.drawable.pic9);
        mDatas.add(R.drawable.pic10);
    }


    public class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_renren, parent, false);


            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Log.i(TAG, "onBindViewHolder: position-->"+position);
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.mImageView.setImageResource(mDatas.get(position));

            myViewHolder.mTvPercent.setText(mDatas.size()-1+"/"+mDatas.size());


        }

        @Override
        public int getItemCount() {
            return mDatas.size()>0?mDatas.size():0;
        }
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv)
        ImageView mImageView;
        @BindView(R.id.tvPercent)
        TextView mTvPercent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}

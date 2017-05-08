package com.booboomx.hotvideo.ui.adapter.xianyu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.xianyu.BannerEntity;
import com.booboomx.hotvideo.databinding.ItemBannerListBinding;

import java.util.List;

/**
 * Created by booboomx on 17/5/7.
 */

public class XianYuBannerAdapter  extends RecyclerView.Adapter<XianYuBannerAdapter.ViewHolder>{

    private Context mContext;
    private List<BannerEntity>mBannerEntities;
    private View.OnClickListener mListener;

    public XianYuBannerAdapter(Context context, List<BannerEntity> bannerEntities) {
        mContext = context;
        mBannerEntities = bannerEntities;
    }

    public void setListener(View.OnClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBannerListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_banner_list, parent, false);

        ViewHolder viewHolder=new ViewHolder(binding);
        if (mListener != null) {
            binding.ivBanner.setOnClickListener(mListener);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mBannerEntities.size()<0){
            return;
        }

        BannerEntity entity = mBannerEntities.get(position % mBannerEntities.size());

        holder.mBinding.setEntity(entity);
        holder.mBinding.ivBanner.setTag(entity);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.mBinding.ivBanner.setOnClickListener(null);
        holder.mBinding.unbind();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemBannerListBinding mBinding;
        public ViewHolder(ItemBannerListBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }
    }
}

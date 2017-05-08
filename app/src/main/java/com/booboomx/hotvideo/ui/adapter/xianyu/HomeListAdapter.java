package com.booboomx.hotvideo.ui.adapter.xianyu;

import android.content.Context;
import android.view.View;

import com.booboomx.hotvideo.bean.xianyu.HomeListEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by booboomx on 17/5/8.
 */

public class HomeListAdapter extends BaseQuickAdapter<HomeListEntity,HomeListAdapter.ViewHolder>{

    private Context mContext;

    public HomeListAdapter(Context context,int layoutResId, List<HomeListEntity> data) {
        super(layoutResId, data);
        this.mContext=context;
    }


    @Override
    protected void convert(ViewHolder helper, HomeListEntity item) {

    }

    public class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

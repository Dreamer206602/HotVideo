package com.booboomx.hotvideo.ui.adapter;

/**
 * Created by booboomx on 17/3/20.
 */

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * 推荐
 */
public class RecommendAdapter extends RecyclerArrayAdapter<VideoInfo>{
    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecommendViewHolder(parent);
    }



    public class RecommendViewHolder extends BaseViewHolder<VideoInfo>{

        private ImageView imgPiture;
        private TextView tvTitle;


        public RecommendViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_video);
            imgPiture=$(R.id.img_video);
            tvTitle=$(R.id.tv_title);

            imgPiture.setScaleType(ImageView.ScaleType.CENTER_CROP);


        }

        @Override
        public void setData(VideoInfo data) {
            super.setData(data);

            tvTitle.setText(data.title);
            ImageLoader.load(getContext(),data.pic,imgPiture);

        }
    }



}

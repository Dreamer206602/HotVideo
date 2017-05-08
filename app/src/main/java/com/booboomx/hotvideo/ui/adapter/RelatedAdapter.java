package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by booboomx on 17/3/21.
 */

public class RelatedAdapter extends RecyclerArrayAdapter<VideoInfo> {
    public RelatedAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RelatedViewHolder(parent);
    }



    public class RelatedViewHolder extends BaseViewHolder<VideoInfo>{


        private ImageView imgPicture;
        private TextView tvTitle;


        public RelatedViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_related);

            imgPicture=$(R.id.img_video);
            tvTitle=$(R.id.tv_title);

            imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);

        }


        @Override
        public void setData(VideoInfo data) {
            super.setData(data);


            tvTitle.setText(data.title);

            ViewGroup.LayoutParams layoutParams = imgPicture.getLayoutParams();

            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

            int width = displayMetrics.widthPixels / 3;//宽度为屏幕高度的一半
            layoutParams.height= (int) (width*1.2);
            imgPicture.setLayoutParams(layoutParams);
            ImageLoader.load(getContext(),data.pic,imgPicture);



        }
    }



}

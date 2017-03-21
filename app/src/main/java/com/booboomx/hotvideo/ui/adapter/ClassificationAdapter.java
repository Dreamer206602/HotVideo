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

public class ClassificationAdapter extends RecyclerArrayAdapter<VideoInfo> {
    public ClassificationAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassificationViewHolder(parent);
    }


    public class ClassificationViewHolder extends BaseViewHolder<VideoInfo>{

        private ImageView imgPicture;
        private TextView tvTitle;

        public ClassificationViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_found);
            imgPicture=$(R.id.img_video);
            tvTitle=$(R.id.tv_title);
            imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        @Override
        public void setData(VideoInfo data) {
            super.setData(data);
            tvTitle.setText(data.title);

            ViewGroup.LayoutParams layoutParams = imgPicture.getLayoutParams();
            DisplayMetrics metrics =
                    getContext().getResources().getDisplayMetrics();

            int width = metrics.widthPixels / 2;


            layoutParams.height= (int) (width/1.8);
            imgPicture.setLayoutParams(layoutParams);
            ImageLoader.load(getContext(),data.pic,imgPicture);



        }
    }
}

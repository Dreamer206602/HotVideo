package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by booboomx on 17/3/22.
 */

public class VideoListAdapter extends RecyclerArrayAdapter<VideoType> {
    public VideoListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListViewHolder(parent);
    }

    public class VideoListViewHolder extends BaseViewHolder<VideoType> {

        private ImageView imgPicture;
        private TextView tvTitle;

        public VideoListViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_related);
            imgPicture = $(R.id.img_video);
            tvTitle = $(R.id.tv_title);
            imgPicture.setScaleType(ImageView.ScaleType.FIT_XY);

        }

        @Override
        public void setData(VideoType data) {
            super.setData(data);
            tvTitle.setText(data.title);


            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

            DisplayMetrics metrics =
                    getContext().getResources().getDisplayMetrics();


            int width = metrics.widthPixels / 3;//宽度为屏幕宽度的一半

            params.height= (int) (width*1.1);
            imgPicture.setLayoutParams(params);

            ImageLoader.load(getContext(),data.pic,imgPicture);



        }
    }


}

package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.gank.GankItemBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by booboomx on 17/3/25.
 */

public class WelfareAdapter extends RecyclerArrayAdapter<GankItemBean> {


   public interface OnPictureClickListener{
        void imageClick(GankItemBean gankItemBean ,ImageView imageView);
    }


    public void setListener(OnPictureClickListener listener) {
        mListener = listener;
    }

    public OnPictureClickListener mListener;





    public WelfareAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WelfareViewHolder(parent);
    }


    public class WelfareViewHolder extends BaseViewHolder<GankItemBean>{

        private ImageView imgPicture;

        public WelfareViewHolder(ViewGroup parent) {
            super(new ImageView(parent.getContext()));
            imgPicture= (ImageView) itemView;
            imgPicture.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            imgPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }

        @Override
        public void setData(final GankItemBean data) {
            super.setData(data);

            imgPicture.setBackgroundResource(R.mipmap.default_200);
            ViewGroup.LayoutParams params = imgPicture.getLayoutParams();

            params.height=data.getHeight();
            imgPicture.setLayoutParams(params);
            Glide.with(getContext())
                    .load(data.getUrl())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPicture);

            if (imgPicture != null) {
                imgPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.imageClick(data,imgPicture);
                    }
                });
            }


        }
    }
}

package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by booboomx on 17/3/21.
 */

public class CommentAdapter extends RecyclerArrayAdapter<VideoType> {
    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(parent);
    }



    public class CommentViewHolder extends BaseViewHolder<VideoType>{

        private RoundedImageView avator;
        private TextView tv_nick,tv_time,tv_like,tv_comment;

        public CommentViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_comment);

            avator=$(R.id.avatar);
            tv_nick=$(R.id.tv_nick);
            tv_time=$(R.id.tv_time);
            tv_like=$(R.id.tv_like);
            tv_comment=$(R.id.tv_comment);

        }

        @Override
        public void setData(VideoType data) {
            super.setData(data);
            tv_nick.setText(data.phoneNumber);
            tv_time.setText(data.time);
            tv_like.setText(data.likeNum);
            tv_comment.setText(data.msg);

            if(!TextUtils.isEmpty(data.userPic)){
                ImageLoader.load(CommentAdapter.this.getContext(),data.userPic,avator);
            }
        }
    }
}

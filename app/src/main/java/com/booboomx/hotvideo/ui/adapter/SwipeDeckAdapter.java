package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by booboomx on 17/3/22.
 */

public class SwipeDeckAdapter extends BaseAdapter {

    private List<VideoType> data;
    private Context mContext;
    private LayoutInflater mInflater;
    private VideoInfo mVideoInfo;

    public SwipeDeckAdapter(List<VideoType> data, Context context) {
        this.data = data;
        this.mContext = context;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return data.size()>0?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){

            convertView=mInflater.inflate(R.layout.card_item,parent,false);

            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        ImageLoader.load(mContext,data.get(position).pic,viewHolder.offerImage);

        String intro="\t\t\t"+data.get(position).description;

        viewHolder.tvIntroduction.setText(intro);

        viewHolder.tv_title.setText(data.get(position).title);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchData(data.get(position));
                JumpUtil.go2VideoInfoActivity(mContext,mVideoInfo);

            }
        });



        return convertView;
    }

    private void switchData(VideoType videoType) {
        if(mVideoInfo==null){
            mVideoInfo=new VideoInfo();

            mVideoInfo.title=videoType.title;
            mVideoInfo.dataId=videoType.dataId;
            mVideoInfo.pic=videoType.pic;
            mVideoInfo.airTime=videoType.airTime;
            mVideoInfo.score=videoType.score;

        }


    }


    public class  ViewHolder{

        @BindView(R.id.offer_image)
        RoundedImageView offerImage;
        @BindView(R.id.tv_introduction)
        TextView tvIntroduction;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.tv_title)
        TextView tv_title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }


    }
}

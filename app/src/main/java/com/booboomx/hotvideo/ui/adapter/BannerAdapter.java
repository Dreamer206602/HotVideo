package com.booboomx.hotvideo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by booboomx on 17/3/20.
 */

public class BannerAdapter extends StaticPagerAdapter {


    private Context mContext;
    private List<VideoInfo>mVideoInfos;

    public BannerAdapter(Context context, List<VideoInfo> videoInfos) {
        mContext = context;
        mVideoInfos = videoInfos;
        removeEmpty(this.mVideoInfos);


    }


    private void removeEmpty(List<VideoInfo>list){


        for (int i = 0; i < list.size(); i++) {

            if(!list.get(i).loadType.equals("video")){
                list.remove(i);
                i--;
            }

        }
    }



    @Override
    public View getView(ViewGroup container, final int position) {

        ImageView imageView=new ImageView(mContext);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.mipmap.default_320);

        //加载图片
        ImageLoader.load(mContext,mVideoInfos.get(position).pic,imageView);
        //点击事件

         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 JumpUtil.go2VideoInfoActivity(mContext,mVideoInfos.get(position));



             }
         });

        return imageView;
    }

    @Override
    public int getCount() {
        return mVideoInfos.size()>0?mVideoInfos.size():0;
    }
}

package com.booboomx.hotvideo.utils;

/**
 * Created by booboomx on 17/3/14.
 */

import android.content.Context;
import android.content.Intent;

import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.ui.activity.MainActivity;
import com.booboomx.hotvideo.ui.activity.TanTanActivity;
import com.booboomx.hotvideo.ui.activity.VideoInfoActivity;
import com.booboomx.hotvideo.ui.activity.VideoListActivity;
import com.booboomx.hotvideo.ui.activity.WelcomeActivity;

/**
 * 挑战工具类
 */
public class JumpUtil {

    public static void go2VideoInfoActivity(Context context, VideoInfo videoInfo) {
        Intent intent = new Intent(context, VideoInfoActivity.class);
        intent.putExtra("videoInfo", videoInfo);
        context.startActivity(intent);
    }

    public static void go2VideoListActivity(Context context, String catalogId, String title) {
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra("catalogId", catalogId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
//    public static void go2VideoListSearchActivity(Context context, String searchStr,String title) {
//        Intent intent = new Intent(context, VideoListActivity.class);
//        intent.putExtra("searchStr", searchStr);
//        intent.putExtra("title", title);
//        context.startActivity(intent);
//    }

    public static void go2MainActivity(Context context) {
        jump(context, MainActivity.class);
        ((WelcomeActivity) context).finish();
    }

    private static void jump(Context a, Class<?> clazz) {
        Intent intent = new Intent(a, clazz);
        a.startActivity(intent);
    }


    public static void  go2TanTanActivity(Context context){
        Intent intent=new Intent(context, TanTanActivity.class);
        context.startActivity(intent);

    }

}

package com.booboomx.hotvideo.utils;

import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.bean.VideoType;

/**
 * Created by booboomx on 17/3/21.
 */

public class BeanUtil {

    public static VideoInfo VideoType2VideoInfo(VideoType videoType, VideoInfo videoInfo) {
        if (videoInfo == null)
            videoInfo = new VideoInfo();
        videoInfo.title = videoType.title;
        videoInfo.dataId = videoType.dataId;
        videoInfo.pic = videoType.pic;
        videoInfo.airTime = videoType.airTime;
        videoInfo.score = videoType.score;
        return videoInfo;
    }

    public static VideoRes VideoInfo2VideoRes(VideoInfo videoInfo, VideoRes videoRes) {
        if (videoRes == null)
            videoRes = new VideoRes();
        videoRes.title = StringUtils.isEmpty(videoInfo.title);
        videoRes.pic = StringUtils.isEmpty(videoInfo.pic);
        videoRes.score = StringUtils.isEmpty(videoInfo.score);
        videoRes.airTime = StringUtils.isEmpty(videoInfo.airTime);
        videoRes.pic = StringUtils.isEmpty(videoInfo.pic);
        return videoRes;
    }
}

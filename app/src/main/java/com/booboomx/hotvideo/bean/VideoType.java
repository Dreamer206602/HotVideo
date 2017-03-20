package com.booboomx.hotvideo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by booboomx on 17/3/19.
 */

public class VideoType {
    public String title;
    public String moreURL;
    public String pic;
    public String dataId;
    public String airTime;
    public String score;
    public String description;
    public String msg;
    public String phoneNumber;
    public String userPic;
    public String time;
    public String likeNum;
    public
    @SerializedName("childList")
    List<VideoInfo> childList;
}

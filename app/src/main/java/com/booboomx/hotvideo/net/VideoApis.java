package com.booboomx.hotvideo.net;

/**
 * Created by booboomx on 17/3/12.
 */

import com.booboomx.hotvideo.bean.VideoRes;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 视频相关
 */
public interface VideoApis {


    String HOST = "http://api.svipmovie.com/front/";

    /**
     * 首页的列表
     * @return
     */
    @GET("homePageApi/homePage.do")
    Observable<VideoHttpResponse<VideoRes>> getHomePage();








}

package com.booboomx.hotvideo.net;

import com.booboomx.hotvideo.bean.gank.GankItemBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by booboomx on 17/3/25.
 */

public interface GankApis {

    String HOST = "http://gank.io/api/";


    /**
     * 福利列表
     */
    @GET("data/福利/{num}/{page}")
    Observable<GankHttpResponse<List<GankItemBean>>> getGirlList(@Path("num") int num, @Path("page") int page);






}

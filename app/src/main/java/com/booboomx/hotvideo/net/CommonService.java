package com.booboomx.hotvideo.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by booboomx on 17/3/27.
 */

public interface CommonService {
    String BASE_URL = "http://www.example.com/";//这个不重要，可以随便写，但是必须有

    @GET
    Observable<ResponseBody> loadString(@Url String url);

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}

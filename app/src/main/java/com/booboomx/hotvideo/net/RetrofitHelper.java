package com.booboomx.hotvideo.net;

import android.util.Log;

import com.booboomx.hotvideo.BuildConfig;
import com.booboomx.hotvideo.app.Constants;
import com.booboomx.hotvideo.utils.KL;
import com.booboomx.hotvideo.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by booboomx on 17/3/19.
 */

public class RetrofitHelper {

    private static OkHttpClient okHttpClient=null;
    private static  VideoApis videoApis;
    public static GankApis gankApis;



    public static VideoApis getVideoApis(){

        initOkHttp();

        if(videoApis==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(VideoApis.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            videoApis=retrofit.create(VideoApis.class);
        }
        return videoApis;




    }

    public static GankApis getGankApis(){
        initOkHttp();
        if(gankApis==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(GankApis.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            gankApis=retrofit.create(GankApis.class);

        }

        return gankApis;
    }

    /**
     *设置网络请求配置
     */
    private static void initOkHttp() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder=new OkHttpClient.Builder();
            if(BuildConfig.DEBUG){

                HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                builder.addInterceptor(loggingInterceptor);

            }

            File cacheFile=new File(Constants.PATH_CACHE);
            Cache cache=new Cache(cacheFile,1024*1024*50);

            Interceptor cacheInterceptor=new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request request = chain.request();

                    Log.i("嗷大喵来打印日志咯", "request:" + request.toString());


                    if(!SystemUtils.isNetworkConnected()){

                        request=request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();

                    }

                    int tryCount=0;
                    Response response = chain.proceed(request);
                    String content = response.toString();

                    Log.i("嗷大喵来打印日志咯", "response body:" + content);


                    while (!response.isSuccessful()&&tryCount<3){
                        KL.d(RetrofitHelper.class, "interceptRequest is not successful - :{}", tryCount);

                        tryCount++;

                        // retry the request
                       response= chain.proceed(request);

                    }

                    if (SystemUtils.isNetworkConnected()) {
                        int maxAge = 0;
                        // 有网络时, 不缓存, 最大保存时长为0
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .removeHeader("Pragma")
                                .build();
                    } else {
                        // 无网络时，设置超时为4周
                        int maxStale = 60 * 60 * 24 * 28;
                        response.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .removeHeader("Pragma")
                                .build();
                    }

                    return response;


                }
            };

            //设置缓存
            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);
            builder.cache(cache);

            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(20,TimeUnit.SECONDS);
            builder.writeTimeout(20,TimeUnit.SECONDS);

            //错误重连
            builder.retryOnConnectionFailure(true);
             okHttpClient = builder.build();

        }


    }


}

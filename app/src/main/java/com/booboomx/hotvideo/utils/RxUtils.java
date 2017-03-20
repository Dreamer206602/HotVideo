package com.booboomx.hotvideo.utils;

import android.text.TextUtils;

import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.net.exception.ApiException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by booboomx on 17/3/12.
 */

public class RxUtils {

    /**
     * 统一处理线程
     */
    public static <T>Observable.Transformer<T,T> rxSchedulerHelper(){

        return new Observable.Transformer<T,T>(){

            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }



    public static <T> Observable.Transformer<VideoHttpResponse<T>,T>handleResult(){

        return new Observable.Transformer<VideoHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<VideoHttpResponse<T>> responseObservable) {

                return responseObservable.flatMap(new Func1<VideoHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(VideoHttpResponse<T> videoHttpResponse) {
                        if(videoHttpResponse.getCode()==200){
                            return createData(videoHttpResponse.getRet());
                        }else if(!TextUtils.isEmpty(videoHttpResponse.getMsg())){
                            return Observable.error(new ApiException("*"+videoHttpResponse.getMsg()));

                        }else{
                            return Observable.error(new ApiException("*"+"服务器返回error"));
                        }

                    }
                });

            }
        };


    }







    /**
     * 生成 Observable
     *
     */
     public static <T> Observable<T> createData(final  T t){

         return Observable.create(new Observable.OnSubscribe<T>() {
             @Override
             public void call(Subscriber<? super T> subscriber) {
                 try{

                     subscriber.onNext(t);
                     subscriber.onCompleted();
                 }catch (Exception e){
                     subscriber.onError(e);

                 }
             }
         });

     }




}

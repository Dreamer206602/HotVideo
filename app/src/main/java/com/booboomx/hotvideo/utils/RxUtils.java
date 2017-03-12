package com.booboomx.hotvideo.utils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

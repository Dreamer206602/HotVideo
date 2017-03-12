package com.booboomx.hotvideo.base;

/**
 * Created by booboomx on 17/3/12.
 */

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于Rx的 Presenter封装，控制声明周期
 * @param <T>
 */
public class RxPresenter<T> implements BasePresenter<T> {
    protected  T mView;
    protected CompositeSubscription mCompositeSubscription;


    protected  void unSubscribe(){
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    protected  void  addSubscribe(Subscription subscription){
        if (subscription == null) {

            mCompositeSubscription=new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);

    }



    @Override
    public void attachView(T view) {
        this.mView=view;


    }

    @Override
    public void detachView() {

        this.mView=null;
        unSubscribe();

    }
}

package com.booboomx.hotvideo.presenter;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.presenter.contract.WelcomeContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by booboomx on 17/3/12.
 */

public class WelcomePresenter extends RxPresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View mView;
    public static final int COUNT_DOWN_TIME=2200;


    public WelcomePresenter(WelcomeContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
        getWelcomeData();

    }


    @Override
    public void getWelcomeData() {
        mView.showContent(getImgData());
        startCountDown();


    }

    private void startCountDown() {


        Subscription subscription= Observable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jumpToMain();
                    }
                });
        addSubscribe(subscription);


    }

    private List<String> getImgData() {
        List<String> imgs = new ArrayList<>();
        imgs.add("file:///android_asset/a.jpg");
        imgs.add("file:///android_asset/b.jpg");
        imgs.add("file:///android_asset/c.jpg");
//        imgs.add("file:///android_asset/d.jpg");
        imgs.add("file:///android_asset/e.jpg");

        imgs.add("file:///android_asset/f.jpg");
        imgs.add("file:///android_asset/g.jpg");
        return imgs;
    }
}

package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.presenter.contract.DiscoverContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.utils.SystemUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by booboomx on 17/3/22.
 */

public class DiscoverPresenter extends RxPresenter implements DiscoverContract.Presenter {

    public static final String TAG = "DiscoverPresenter";
    @NonNull
    DiscoverContract.View mView;

    final String catalogId = "402834815584e463015584e53843000b";
    int max = 90;
    int min = 1;


    public DiscoverPresenter(@NonNull DiscoverContract.View threeView) {
        mView = Preconditions.checkNotNull(threeView);
        mView.setPresenter(this);
    }

    @Override
    public void getData() {

        getNextVideos();


    }

    private void getNextVideos() {

        Subscription subscription = RetrofitHelper.getVideoApis().getVideoList(catalogId, getNextPage() + "")
                .compose(RxUtils.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtils.<VideoRes>handleResult())
                .subscribe(new Subscriber<VideoRes>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.i(TAG, "onStart: ");
                    }

                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());

                        mView.refreshFail(StringUtils.getErrorMsg(e.getMessage()));

                        if (mView.isActive()) {
                            mView.hideLoading();
                        }


                    }

                    @Override
                    public void onNext(VideoRes videoRes) {
                        if (videoRes.list != null && videoRes.list.size() > 0) {
                            if (mView.isActive()) {
                                mView.hideLoading();
                                mView.showContent(videoRes);
                            }
                        }

                    }
                });

        addSubscribe(subscription);


    }


    private int getNextPage() {

        int page = mView.getLastPage();
        if (SystemUtils.isNetworkConnected()) {

            page = StringUtils.getRandomNumber(min, max);
            mView.setLasePage(page);

        }

        return page;


    }


}

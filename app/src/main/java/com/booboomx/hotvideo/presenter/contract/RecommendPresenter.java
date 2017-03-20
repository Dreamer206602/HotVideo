package com.booboomx.hotvideo.presenter.contract;

import android.support.annotation.NonNull;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by booboomx on 17/3/19.
 */

public class RecommendPresenter extends RxPresenter implements RecommendContract.Presenter{

    private RecommendContract.View mView;
    private int page=0;

    public RecommendPresenter(@NonNull  RecommendContract.View view) {
        mView = Preconditions.checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        page=0;
        getPageDataInfo();

    }

    private void getPageDataInfo() {

        Subscription subscription= RetrofitHelper.getVideoApis().getHomePage()
                .compose(RxUtils.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtils.<VideoRes>handleResult())
                .subscribe(new Action1<VideoRes>() {
                    @Override
                    public void call(VideoRes videoRes) {
                        if (videoRes != null) {

                            if (mView.isActive()) {
                                mView.showContent(videoRes);
                            }

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        mView.refreshFaile(StringUtils.getErrorMsg(throwable.getMessage()));
                    }
                });
        addSubscribe(subscription);



    }
}

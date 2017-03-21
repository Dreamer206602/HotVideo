package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.presenter.contract.ClassificationContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by booboomx on 17/3/21.
 */

public class ClassificationPresenter extends RxPresenter implements ClassificationContract.Presenter {

    public static final String TAG="Classification";

   ClassificationContract.View mView;
    private int page=0;

    public ClassificationPresenter(@NonNull ClassificationContract.View twoView) {
        mView = Preconditions.checkNotNull(twoView);
        mView.setPresenter(this);
    }

    @Override
    public void refresh() {
        page=0;
        getPageHomeInfo();

    }

    private void getPageHomeInfo() {

        Subscription subscription= RetrofitHelper.getVideoApis().getHomePage()
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

                        Log.i(TAG, "onError: "+e.getMessage());
                        if(mView.isActive()){
                            mView.refreshFail(StringUtils.getErrorMsg(e.getMessage()));
                        }

                    }

                    @Override
                    public void onNext(VideoRes videoRes) {

                        if(videoRes.list!=null&&videoRes.list.size()>0){
                            if(mView.isActive()){
                                mView.showContent(videoRes);


                            }

                        }



                    }
                });
        addSubscribe(subscription);





    }
}

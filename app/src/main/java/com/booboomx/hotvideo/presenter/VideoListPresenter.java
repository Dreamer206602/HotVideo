package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.presenter.contract.VideoListContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by booboomx on 17/3/22.
 */

public class VideoListPresenter extends RxPresenter implements VideoListContract.Presenter {
   public static final String TAG="VideoListPresenter";

    @NonNull
    final VideoListContract.View mView;

    private int page = 1;

    private String catalogId = "";


    public VideoListPresenter(@NonNull VideoListContract.View addTaskView, String catalogId) {
        mView = Preconditions.checkNotNull(addTaskView);
        mView.setPresenter(this);
        this.catalogId = catalogId;
        onRefresh();

    }

    @Override
    public void onRefresh() {
        page = 1;
        getVideoList(catalogId);

    }

    private void getVideoList(String catalogId) {

        Subscription subscription = RetrofitHelper.getVideoApis().getVideoList(catalogId, page + "")
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

                        if(page>1){
                            page--;

                        }
                        mView.refreshFail(StringUtils.getErrorMsg(e.getMessage()));

                    }

                    @Override
                    public void onNext(VideoRes videoRes) {

                        if (videoRes != null) {

                            if(mView.isActive()){

                                if(page==1){
                                    mView.showContent(videoRes.list);
                                }else{
                                    mView.showMoreContent(videoRes.list);
                                }

                            }

                        }

                    }
                });

        addSubscribe(subscription);


    }

    @Override
    public void loadMore() {
        page++;
        getVideoList(catalogId);

    }
}

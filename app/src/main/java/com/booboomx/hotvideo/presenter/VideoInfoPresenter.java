package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.presenter.contract.VideoInfoContract;
import com.booboomx.hotvideo.utils.BeanUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by booboomx on 17/3/21.
 */

public class VideoInfoPresenter extends RxPresenter implements VideoInfoContract.Presenter {

    public static final String TAG="VideoInfoPresenter";

    public final static String Refresh_Video_Info = "Refresh_Video_Info";
    public final static String Put_DataId = "Put_DataId";
    public static final String Refresh_Collection_List = "Refresh_Collection_List";
    public static final String Refresh_History_List = "Refresh_History_List";
    final int WAIT_TIME = 200;
    VideoRes result;
    String dataId = "";
    String pic = "";



    @NonNull
    final  VideoInfoContract.View mView;

    public VideoInfoPresenter(@NonNull VideoInfoContract.View addTaskView, VideoInfo videoInfo) {
        mView = Preconditions.checkNotNull(addTaskView);
        mView.setPresenter(this);

        mView.showContent(BeanUtil.VideoInfo2VideoRes(videoInfo,null));


        this.dataId=videoInfo.dataId;
        this.pic=videoInfo.pic;
        getDetailData(videoInfo.dataId);

        setCollectState();
        putMediaId();
    }

    private void putMediaId() {



        Subscription subscription= Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        EventBus.getDefault().post(dataId,Put_DataId);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscribe(subscription);






    }



    @Override
    public void getDetailData(String dataId) {


        Subscription subscription= RetrofitHelper.getVideoApis().getVideoInfo(dataId)
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
                            mView.hideLoading();
                            mView.showError("数据加载失败");
                        }



                    }

                    @Override
                    public void onNext(VideoRes videoRes) {
                        Log.i(TAG, "onNext: "+videoRes.getVideoUrl());

                        if (videoRes != null) {

                            if(mView.isActive()){
                                mView.showContent(videoRes);
                                result=videoRes;
                                postData();

                                //存进数据库
                                insertRecord();

                            }

                        }

                    }
                });

        addSubscribe(subscription);





    }

    private void postData() {

        Subscription subscription= Observable.timer(WAIT_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtils.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        EventBus.getDefault().post(result,Refresh_Video_Info);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscribe(subscription);




    }

    @Override
    public void collect() {

    }

    @Override
    public void insertRecord() {

    }



    private void setCollectState() {




    }
}

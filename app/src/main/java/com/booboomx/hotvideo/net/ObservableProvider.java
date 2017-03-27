package com.booboomx.hotvideo.net;

import com.booboomx.hotvideo.utils.RxUtils;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by booboomx on 17/3/27.
 */

public class ObservableProvider {

    private CommonService mCommonService;

    private static class DefaultHolder {
        private static ObservableProvider INSTANCE = new ObservableProvider();
    }

    private ObservableProvider() {
        mCommonService = ServiceFactory.getInstance().createService(CommonService.class);

    }

    public static ObservableProvider getDefault() {
        return DefaultHolder.INSTANCE;
    }


    public void download(String url, final DownLoadSubscribe subscribe) {
        mCommonService
                .download(url)
                .compose(RxUtils.<ResponseBody>all_io())
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        subscribe.writeResponseBodyToDisk(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        subscribe.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscribe.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //do nothing
                    }
                });

    }

}

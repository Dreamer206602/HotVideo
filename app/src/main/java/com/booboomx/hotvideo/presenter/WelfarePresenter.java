package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.gank.GankItemBean;
import com.booboomx.hotvideo.net.GankHttpResponse;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.presenter.contract.WelfareContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by booboomx on 17/3/25.
 */

public class WelfarePresenter extends RxPresenter implements WelfareContract.Presenter {

    @NonNull
    WelfareContract.View mView;

    public static final int NUM_OF_PAGE = 20;

    private int currentPage = 1;


    public WelfarePresenter(@NonNull WelfareContract.View welfareView) {
        mView = Preconditions.checkNotNull(welfareView);
        mView.setPresenter(this);
        onRefresh();
    }

    @Override
    public void onRefresh() {

        currentPage = 1;
        Subscription subscription = RetrofitHelper.getGankApis().getGirlList(NUM_OF_PAGE, currentPage)
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<GankItemBean>>handleGankResult())
                .subscribe(new Subscriber<List<GankItemBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                        mView.showError("数据加载失败ヽ(≧Д≦)ノ");


                    }

                    @Override
                    public void onNext(List<GankItemBean> list) {

                        setHeight(list);
                        mView.showContent(list);

                    }
                });

        addSubscribe(subscription);

    }

    @Override
    public void loadMore() {


        Subscription subscription = RetrofitHelper.getGankApis().getGirlList(NUM_OF_PAGE, ++currentPage)
                .compose(RxUtils.<GankHttpResponse<List<GankItemBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<GankItemBean>>handleGankResult())
                .subscribe(new Subscriber<List<GankItemBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        mView.showError("加载更多数据失败ヽ(≧Д≦)ノ");


                    }

                    @Override
                    public void onNext(List<GankItemBean> list) {

                        setHeight(list);
                        mView.loadMoreContent(list);

                    }
                });

        addSubscribe(subscription);

    }

    private void setHeight(List<GankItemBean> list) {

        DisplayMetrics metrics =
                mView.getContext().getResources().getDisplayMetrics();


        int width = metrics.widthPixels / 2;

        for (GankItemBean ben : list) {

            ben.setHeight(width* StringUtils.getRandomNumber(3,6)/3);//随机的高度

        }


    }
}

package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.net.RetrofitHelper;
import com.booboomx.hotvideo.net.VideoHttpResponse;
import com.booboomx.hotvideo.presenter.contract.SearchVideoListContract;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.RxUtils;
import com.booboomx.hotvideo.utils.StringUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by booboomx on 17/3/24.
 */

public class SearchVideoListPresenter extends RxPresenter implements SearchVideoListContract.Presenter {
    @NonNull
    SearchVideoListContract.View mView;


    private int page = 1;

    private String searchStr = "";


    public SearchVideoListPresenter(@NonNull SearchVideoListContract.View searchView, List<VideoInfo> list) {
        mView = Preconditions.checkNotNull(searchView);
        mView.setPresenter(this);
        mView.showRecommend(list);
    }

    @Override
    public void onRefresh() {

        page = 1;
        if (searchStr != null && !searchStr.equals("")) {
            getSearchVideoList(searchStr);
        }

    }

    private void getSearchVideoList(String searchStr) {

        Subscription subscription = RetrofitHelper.getVideoApis().getVideoListByKeyWord(searchStr, page + "")
                .compose(RxUtils.<VideoHttpResponse<VideoRes>>rxSchedulerHelper())
                .compose(RxUtils.<VideoRes>handleResult())
                .subscribe(new Subscriber<VideoRes>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if (page > 1) {
                            page--;
                        }

                        mView.refreshFail(StringUtils.getErrorMsg(e.getMessage()));

                    }

                    @Override
                    public void onNext(VideoRes videoRes) {
                        if (videoRes != null) {

                            if (mView.isActive()) {

                                if (page == 1) {

                                    mView.shwoContent(videoRes.list);

                                } else {
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
        if (searchStr != null && !searchStr.equals("")) {
            getSearchVideoList(searchStr);
        }


    }

    @Override
    public void setSearchKey(String searchKey) {
        this.searchStr = searchKey;

    }
}

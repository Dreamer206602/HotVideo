package com.booboomx.hotvideo.presenter.contract;

import android.content.Context;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.gank.GankItemBean;

import java.util.List;

/**
 * Created by booboomx on 17/3/25.
 */

public interface WelfareContract {


    interface View extends BaseView<Presenter>{


        boolean isActive();

        void refreshFail(String msg);

        void loadMoreFail(String msg);


        Context getContext();

        void  showContent(List<GankItemBean>list);


        void loadMoreContent(List<GankItemBean>list);

    }



    interface  Presenter extends BasePresenter{


        void onRefresh();


        void loadMore();
    }
}

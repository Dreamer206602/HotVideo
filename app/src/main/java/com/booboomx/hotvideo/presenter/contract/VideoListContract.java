package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoType;

import java.util.List;

/**
 * Created by booboomx on 17/3/22.
 */

public interface VideoListContract {


    interface  View extends BaseView<Presenter>{

        boolean isActive();

        void showTitle(String title);

        void  refreshFail(String msg);

        void  loadMoreFail(String msg);

        void showContent(List<VideoType>list);


        void  showMoreContent(List<VideoType>list);

    }


    interface  Presenter extends BasePresenter{

        void onRefresh();


        void loadMore();

    }

}

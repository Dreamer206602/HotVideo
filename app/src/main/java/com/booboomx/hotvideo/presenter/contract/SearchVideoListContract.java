package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;

import java.util.List;

/**
 * Created by booboomx on 17/3/24.
 */

public interface SearchVideoListContract {



    interface  View extends BaseView<Presenter>{

        boolean isActive();

        void  refreshFail(String msg);

        void  loadMoreFail(String msg);


        void  shwoContent(List<VideoType>list);


        void  showMoreContent(List<VideoType>list);

        void showRecommend(List<VideoInfo>list);

    }

    interface Presenter extends BasePresenter {

        void onRefresh();

        void loadMore();


        void setSearchKey(String searchKey);


    }
}

package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoRes;

/**
 * Created by booboomx on 17/3/19.
 */

public interface RecommendContract {

    interface  View extends BaseView<Presenter>{

        boolean isActive();
        void showContent(VideoRes videoRes);

        void refreshFaile(String msg);
        void stopBanner(boolean stop);


    }



    interface  Presenter extends BasePresenter{
        void onRefresh();
    }

}

package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoRes;

/**
 * Created by booboomx on 17/3/21.
 */

public interface VideoInfoContract {

    interface  View extends BaseView<Presenter>{

        void showContent(VideoRes videoRes);


        boolean isActive();

        void  hideLoading();

        void collected();

        void disCollect();



    }


    interface  Presenter extends BasePresenter{


        void getDetailData(String dataId);


        void collect();


        void insertRecord();


    }


}

package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoType;

import java.util.List;

/**
 * Created by booboomx on 17/3/24.
 */

public interface CollectionContract {

    interface  View extends BaseView<Presenter>{

        boolean isActivie();


        void  showContent(List<VideoType>list);
    }




    interface Presenter extends BasePresenter{


        void  getCollectData();

        void  delAllData();


        void  getRecordData();


        int getType();

    }

}

package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoType;

import java.util.List;

/**
 * Created by booboomx on 17/3/23.
 */

public interface MineContract {




    interface  View extends BaseView<Presenter>{

        boolean isActive();
        void showContent(List<VideoType>list);


    }

    interface  Presenter extends BasePresenter{


        void getHistoryData();

        void delAllHistory();
    }


}

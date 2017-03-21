package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoRes;

/**
 * Created by booboomx on 17/3/21.
 */

public interface ClassificationContract {

    interface  View extends BaseView<Presenter>{

        boolean isActive();
        void  showContent(VideoRes videoRes);

        void  refreshFail(String msg);
    }



    interface Presenter extends BasePresenter{

        void refresh();
    }
}

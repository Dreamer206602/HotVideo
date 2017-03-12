package com.booboomx.hotvideo.presenter.contract;

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;

import java.util.List;

/**
 * Created by booboomx on 17/3/12.
 */

public interface WelcomeContract {

    interface  Presenter extends BasePresenter{
        void getWelcomeData();
    }

    interface  View extends BaseView<Presenter>{

        boolean isActive();
        void showContent(List<String>list);

        void jumpToMain();

    }
}

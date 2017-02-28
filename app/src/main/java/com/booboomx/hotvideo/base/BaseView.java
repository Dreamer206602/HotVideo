package com.booboomx.hotvideo.base;

/**
 * Created by booboomx on 17/2/28.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);
    void showError(String msg);


}

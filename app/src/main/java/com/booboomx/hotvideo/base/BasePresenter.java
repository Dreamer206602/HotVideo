package com.booboomx.hotvideo.base;

/**
 * Created by booboomx on 17/2/28.
 */

public interface BasePresenter<T> {

    void attachView(T view);
    void detachView();




}

package com.booboomx.hotvideo.mvvm.binding;

import android.databinding.ObservableList;

/**
 * Created by booboomx on 17/5/7.
 */

public class ListBinding implements IBinding{

    private ObservableList mObservableList;
    private ObservableList.OnListChangedCallback mCallback;

    public ListBinding(ObservableList observableList, ObservableList.OnListChangedCallback callback) {
        mObservableList = observableList;
        mCallback = callback;
    }

    @Override
    public void bind() {
        mObservableList.addOnListChangedCallback(mCallback);

    }

    @Override
    public void unBind() {

        mObservableList.removeOnListChangedCallback(mCallback);

        mCallback=null;
        mObservableList=null;


    }
}

package com.booboomx.hotvideo.mvvm.binding;

import android.databinding.Observable;

/**
 * Created by booboomx on 17/5/7.
 */

public class PropertyBinding implements IBinding {
    private Observable mObservable;
    private Observable.OnPropertyChangedCallback mCallback;

    public PropertyBinding(Observable observable, Observable.OnPropertyChangedCallback callback) {
        mObservable = observable;
        mCallback = callback;
    }

    @Override
    public void bind() {
        mObservable.addOnPropertyChangedCallback(mCallback);

    }

    @Override
    public void unBind() {
        mObservable.removeOnPropertyChangedCallback(mCallback);
        mObservable=null;
        mCallback=null;

    }
}

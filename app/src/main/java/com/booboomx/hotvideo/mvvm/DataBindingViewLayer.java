package com.booboomx.hotvideo.mvvm;

import android.databinding.ViewDataBinding;

import com.booboomx.hotvideo.mvvm.binding.IBinding;

/**
 * Created by booboomx on 17/5/7.
 */

public abstract class DataBindingViewLayer<DB extends ViewDataBinding,VM extends IBinding,Container>extends ViewLayer<VM,Container>{

    public DB  mBinding;


    public DataBindingViewLayer(DB bind,Container container) {
        super(container);
        mBinding=bind;
    }

    @Override
    public void onDetach() {
        mBinding.unbind();
    }
}

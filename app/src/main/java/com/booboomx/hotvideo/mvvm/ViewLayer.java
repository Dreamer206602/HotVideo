package com.booboomx.hotvideo.mvvm;

import com.booboomx.hotvideo.mvvm.binding.IBinding;

/**
 * Created by booboomx on 17/5/7.
 */

public abstract class ViewLayer<VM extends IBinding,Container> {

    protected  Container mContainer;
    public ViewLayer(Container container){
        mContainer=container;
    }


    public abstract void onAttach(VM viewModel);

    public abstract void  onDetach();

}

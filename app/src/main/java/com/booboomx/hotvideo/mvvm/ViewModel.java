package com.booboomx.hotvideo.mvvm;

import android.databinding.Observable;
import android.databinding.ObservableList;

import com.booboomx.hotvideo.mvvm.binding.IBinding;
import com.booboomx.hotvideo.mvvm.binding.ObservableBindingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booboomx on 17/5/7.
 */

public abstract  class ViewModel implements IBinding{

    private ViewLayer mViewLayer;
    private List<IBinding>mIBindings;


    public ViewModel(ViewLayer viewLayer){
        mViewLayer=viewLayer;
    }


    protected abstract void onAttach();

    protected abstract void onDetach();



    @Override
    public void bind() {
        mIBindings=new ArrayList<>();
        onAttach();
        if (mViewLayer != null) {
            mViewLayer.onAttach(this);
        }

    }


    @Override
    public void unBind() {

        onDetach();

        for (int i = 0; i < mIBindings.size(); i++) {

            IBinding binding = mIBindings.get(i);
            binding.unBind();
        }

        mIBindings=null;

        if (mViewLayer != null) {
            mViewLayer.onDetach();
            mViewLayer=null;
        }

    }


    public void  addObservableBinding(Observable observable,Observable.OnPropertyChangedCallback callback){

        IBinding bind = ObservableBindingUtil.bind(observable, callback);
        mIBindings.add(bind);
    }

    public void  addObservableListBinding(ObservableList list,ObservableList.OnListChangedCallback callback){
        IBinding binding = ObservableBindingUtil.bind(list, callback);
        mIBindings.add(binding);
    }




}

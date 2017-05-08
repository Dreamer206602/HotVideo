package com.booboomx.hotvideo.mvvm.binding;

import android.databinding.Observable;
import android.databinding.ObservableList;

/**
 * Created by booboomx on 17/5/7.
 */

public class ObservableBindingUtil {
    public ObservableBindingUtil() {
    }


    public static IBinding bind(Observable observable,Observable.OnPropertyChangedCallback callback){

        IBinding binding=new PropertyBinding(observable,callback);
        binding.bind();
        return binding;
    }

    public static IBinding bind(ObservableList list,ObservableList.OnListChangedCallback callback){
        IBinding binding=new ListBinding(list,callback);
        binding.bind();
        return  binding;
    }
}

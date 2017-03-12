package com.booboomx.hotvideo.utils;

import android.support.annotation.Nullable;

/**
 * Created by booboomx on 17/3/12.
 */

public class Preconditions {

    public static <T> T checkNotNull(T reference){

        if (reference == null) {

            throw new NullPointerException();

        }else{
            return reference;
        }
    }


    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage){

        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));

        }else{
            return reference;
        }
    }





}

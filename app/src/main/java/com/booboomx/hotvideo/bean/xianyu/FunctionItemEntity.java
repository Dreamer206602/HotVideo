package com.booboomx.hotvideo.bean.xianyu;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.booboomx.hotvideo.BR;

/**
 * Created by booboomx on 17/5/7.
 */

public class FunctionItemEntity extends BaseObservable{


    private String iconUrl;
    private String title;
    private String desc;
    private Class cls;

    @Bindable
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        notifyPropertyChanged(BR.iconUrl);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }
}

package com.booboomx.hotvideo.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by booboomx on 17/3/16.
 */

public class ColorRelativeLayout extends RelativeLayout implements ColorUiInterface{

    private  int attr_background=-1;

    public ColorRelativeLayout(Context context) {
        super(context);
    }

    public ColorRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.attr_background=ViewAttributeUtil.getBackgroundAttibute(attrs);

    }

    public ColorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        this.attr_background=ViewAttributeUtil.getBackgroundAttibute(attrs);


    }



    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme theme) {
        if(attr_background!=-1){
            ViewAttributeUtil.applyBackgroundDrawable(this,theme,attr_background);
        }

    }
}

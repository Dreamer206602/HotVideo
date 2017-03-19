package com.booboomx.hotvideo.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.app.App;
import com.booboomx.hotvideo.utils.KL;
import com.booboomx.hotvideo.utils.PreUtils;
import com.booboomx.hotvideo.widget.theme.Theme;

import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by booboomx on 17/2/28.
 */

public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity {


    protected Unbinder mUnBinder;
    public T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KL.d(this.getClass(), this.getClass().getName() + "--------->onCreate");


        init();

    }


    public void init() {

        setTranslucentStatus(true);
        onPreCreate();
        App.getInstance().registerActivities(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onStart");
        setTitleHeight(getRootView(this));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onRestart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onStop");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KL.d(this.getClass(), this.getClass().getName() + "----------->onDestroy");
        App.getInstance().unRegisterActivity(this);


        if (mUnBinder != null)
            mUnBinder.unbind();

        mPresenter = null;


    }

    private void onPreCreate() {

        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);

                break;
            case Brown:
                setTheme(R.style.BrownTheme);

                break;
            case Green:
                setTheme(R.style.GreenTheme);

                break;
            case Purple:
                setTheme(R.style.PurpleTheme);

                break;
            case Teal:
                setTheme(R.style.TealTheme);

                break;
            case Pink:
                setTheme(R.style.PinkTheme);

                break;
            case DeepPurple:
                setTheme(R.style.DeepPurpleTheme);

                break;
            case Orange:
                setTheme(R.style.OrangeTheme);

                break;
            case Indigo:
                setTheme(R.style.IndigoTheme);

                break;
            case LightGreen:
                setTheme(R.style.LightGreenTheme);

                break;
            case Lime:
                setTheme(R.style.LimeTheme);

                break;
            case DeepOrange:
                setTheme(R.style.DeepOrangeTheme);

                break;
            case Black:
                setTheme(R.style.BlackTheme);

                break;


        }


    }

    /**
     * 设置沉浸式状态栏
     *
     * @param b
     */
    private void setTranslucentStatus(boolean b) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (b) {
                attributes.flags |= status;

            } else {
                attributes.flags &= ~status;
            }

            window.setAttributes(attributes);


        }


    }

    private void setTitleHeight(View rootView) {


    }


    public View getRootView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }
}

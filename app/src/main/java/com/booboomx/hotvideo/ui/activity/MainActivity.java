package com.booboomx.hotvideo.ui.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseActivity;
import com.booboomx.hotvideo.ui.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements ColorChooserDialog.ColorCallback{


    @BindView(R.id.main_view)
    MainView mMainView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnBinder= ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {

    }
}

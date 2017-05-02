package com.booboomx.hotvideo.ui.activity;

import android.content.Intent;
import android.view.View;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.SwipeBackActivity;
import com.booboomx.hotvideo.widget.ColorTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class TanTanActivity extends SwipeBackActivity {

    @BindView(R.id.title_name)
    ColorTextView titleName;

   @OnClick(R.id.btn_tantan1)
   public void onClick(View view){
       switch (view.getId()){
           case R.id.btn_tantan1:
               Intent intent=new Intent(TanTanActivity.this,TanTan1Activity.class);
               startActivity(intent);
               finish();
               break;
       }
   }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_tan_tan;
    }

    @Override
    protected void onBaseCreate() {
        titleName.setText("仿探探首页滑动效果");

    }
}

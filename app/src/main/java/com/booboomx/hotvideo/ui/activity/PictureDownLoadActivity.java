package com.booboomx.hotvideo.ui.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.app.App;
import com.booboomx.hotvideo.net.DownLoadSubscribe;
import com.booboomx.hotvideo.net.ObservableProvider;
import com.booboomx.hotvideo.utils.FileUtils;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.SnackBarUtils;
import com.booboomx.hotvideo.utils.SystemShareUtils;
import com.wingsofts.dragphotoview.DragPhotoView;

import java.io.File;

/**
 * 图片下载的界面
 */
public class PictureDownLoadActivity extends AppCompatActivity {

    private AppCompatImageView mIvDownload;

    private String imgUrl;

    private DragPhotoView mPhotoView;

    private String mSavePath;
    private RelativeLayout mRlBottom;


    int mOriginLeft;
    int mOriginTop;
    int mOriginHeight;
    int mOriginWidth;
    int mOriginCenterX;
    int mOriginCenterY;

    private float mTargetHeight;
    private float mTargetWidth;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture_down_load);
        getIntentData();
        mPhotoView= (DragPhotoView) findViewById(R.id.photoView);
        mIvDownload= (AppCompatImageView) findViewById(R.id.iv_download);
        mRlBottom= (RelativeLayout) findViewById(R.id.rl_bottom);

        ImageLoader.loadAllImage(PictureDownLoadActivity.this,imgUrl,mPhotoView);


        mPhotoView.setOnTapListener(new DragPhotoView.OnTapListener() {
            @Override
            public void onTap(DragPhotoView view) {
                finishWithAnimation();
            }
        });

        mPhotoView.setOnExitListener(new DragPhotoView.OnExitListener() {
            @Override
            public void onExit(DragPhotoView view, float x, float y, float w, float h) {
                performExitAnimation(view, x, y, w, h);
            }
        });

        mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadPicture(0);


            }
        });



    }


    public static final String TAG="PictureDownLoad";
    private void downloadPicture(final  int action) {

        mSavePath = FileUtils.getSaveImagePath(this) + File.separator + FileUtils.getFileName(imgUrl);
        Log.i(TAG, "downloadPicture: imgUrl->"+imgUrl);
        Log.i(TAG, "downloadPicture: mSavePath->"+mSavePath);

        ObservableProvider.getDefault().download(imgUrl, new DownLoadSubscribe(FileUtils.getSaveImagePath(this), FileUtils.getFileName(imgUrl)) {
            @Override
            public void onCompleted(File file) {
                if (action == 0) {
                    SnackBarUtils.makeLong(mRlBottom, "已保存至相册").info();
                    MediaScannerConnection.scanFile(App.getInstance(), new String[]{
                                    mSavePath},
                            null, null);
                } else {
                    SystemShareUtils.shareImage(PictureDownLoadActivity.this, Uri.parse(file.getAbsolutePath()));
                }
            }

            @Override
            public void onError(Throwable e) {
                if (action == 0)
                    SnackBarUtils.makeLong(mRlBottom, "保存失败:" + e).danger();
            }

            @Override
            public void onProgress(double progress, long downloadByte, long totalByte) {
                Log.i(TAG, "totalByte:" + totalByte + " downloadedByte:" + downloadByte + " progress:" + progress);

            }
        });

    }

    private void getIntentData() {


        imgUrl=getIntent().getStringExtra("imageUrl");
        mOriginLeft = getIntent().getIntExtra("left", 0);
        mOriginTop = getIntent().getIntExtra("top", 0);
        mOriginHeight = getIntent().getIntExtra("height", 0);
        mOriginWidth = getIntent().getIntExtra("width", 0);
        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
        mOriginCenterY = mOriginTop + mOriginHeight / 2;

    }


    /**
     * ===================================================================================
     * <p>
     * 底下是低版本"共享元素"实现   不需要过分关心  如有需要 可作为参考.
     * <p>
     * Code  under is shared transitions in all android versions implementation
     */
    private void performExitAnimation(final DragPhotoView view, float x, float y, float w, float h) {
        view.finishAnimationCallBack();
        float viewX = mTargetWidth / 2 + x - mTargetWidth * mScaleX / 2;
        float viewY = mTargetHeight / 2 + y - mTargetHeight * mScaleY / 2;
        view.setX(viewX);
        view.setY(viewY);

        float centerX = view.getX() + mOriginWidth / 2;
        float centerY = view.getY() + mOriginHeight / 2;

        float translateX = mOriginCenterX - centerX;
        float translateY = mOriginCenterY - centerY;


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), view.getX() + translateX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), view.getY() + translateY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();
    }

    private void finishWithAnimation() {

        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(0, mTranslationX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(0, mTranslationY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(1, mScaleY);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(1, mScaleX);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });

        scaleXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }

    private void performEnterAnimation() {
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(mPhotoView.getX(), 0);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(mPhotoView.getY(), 0);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(mScaleY, 1);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(mScaleX, 1);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPhotoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }

    @Override
    public void onBackPressed() {
        finishWithAnimation();
    }

}

package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.presenter.contract.MineContract;
import com.booboomx.hotvideo.utils.Preconditions;

/**
 * Created by booboomx on 17/3/23.
 */

public class MinePresenter extends RxPresenter implements MineContract.Presenter {

    @NonNull
    MineContract.View mView;
    public static final int maxSize=30;

    public MinePresenter(@NonNull MineContract.View mineView) {
        mView = Preconditions.checkNotNull(mineView);
        mView.setPresenter(this);
        getHistoryData();
    }

    @Override
    public void getHistoryData() {



    }

    @Override
    public void delAllHistory() {

    }
}

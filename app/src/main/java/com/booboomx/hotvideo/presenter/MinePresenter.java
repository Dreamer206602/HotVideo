package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.bean.db.Record;
import com.booboomx.hotvideo.db.RealmHelper;
import com.booboomx.hotvideo.presenter.contract.MineContract;
import com.booboomx.hotvideo.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

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

        List<Record> records = RealmHelper.getInstance().getRecordList();
        List<VideoType> list = new ArrayList<>();
        VideoType videoType;
        int maxlinth = records.size() <= 3 ? records.size() : 3;
        for (int i = 0; i < maxlinth; i++) {
            Record record = records.get(i);
            videoType = new VideoType();
            videoType.title = record.title;
            videoType.pic = record.pic;
            videoType.dataId = record.getId();
            list.add(videoType);
        }
        mView.showContent(list);




    }

    @Override
    public void delAllHistory() {

        RealmHelper.getInstance().deleteAllRecord();


    }
}

package com.booboomx.hotvideo.presenter;

import android.support.annotation.NonNull;

import com.booboomx.hotvideo.base.RxPresenter;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.bean.db.Collection;
import com.booboomx.hotvideo.bean.db.Record;
import com.booboomx.hotvideo.db.RealmHelper;
import com.booboomx.hotvideo.presenter.contract.CollectionContract;
import com.booboomx.hotvideo.utils.Preconditions;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booboomx on 17/3/24.
 */

public class CollectionPresenter extends RxPresenter implements CollectionContract.Presenter {
    @NonNull
    CollectionContract.View mView;

    int type = 0;//收藏 0，历史：1

    public CollectionPresenter(@NonNull CollectionContract.View collectView, int type) {
        mView = Preconditions.checkNotNull(collectView);
        mView.setPresenter(this);
        this.type = type;


        if (type == 0) {

            getCollectData();

        } else {

            getRecordData();

        }
    }


    @Override
    public void getCollectData() {

        List<Collection> collections = RealmHelper.getInstance().getCollectionList();
        List<VideoType> list = new ArrayList<>();
        VideoType videoType;
        for (Collection collection : collections) {
            videoType = new VideoType();
            videoType.title = collection.title;
            videoType.pic = collection.pic;
            videoType.dataId = collection.getId();
            videoType.score = collection.getScore();
            videoType.airTime = collection.getAirTime();
            list.add(videoType);
        }
        mView.showContent(list);
    }

    @Override
    public void delAllData() {

        if(type==0){

            RealmHelper.getInstance().deleteAllCollection();

        }else{
            RealmHelper.getInstance().deleteAllRecord();

            EventBus.getDefault().post("",VideoInfoPresenter.Refresh_History_List);
        }

    }

    @Override
    public void getRecordData() {

        List<Record> recordList =
                RealmHelper.getInstance().getRecordList();

        List<VideoType> list = new ArrayList<>();

        VideoType videoType;


        for (Record record : recordList) {
            videoType = new VideoType();
            videoType.title  = record.title;
            videoType.pic = record.pic;
            videoType.dataId = record.getId();
            list.add(videoType);


        }

        mView.showContent(list);

    }

    @Override
    public int getType() {
        return type;
    }
}

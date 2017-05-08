package com.booboomx.hotvideo.model.impl;

import android.databinding.Observable;
import android.databinding.ObservableList;

import com.booboomx.hotvideo.bean.xianyu.BannerEntity;
import com.booboomx.hotvideo.bean.xianyu.FunctionItemEntity;
import com.booboomx.hotvideo.bean.xianyu.HomeListEntity;
import com.booboomx.hotvideo.model.IHomeViewModel;

import java.util.List;

/**
 * Created by booboomx on 17/5/7.
 */

public class HomeViewModel implements IHomeViewModel {
    @Override
    public void bind() {

    }

    @Override
    public void unBind() {

    }

    @Override
    public void startRefresh(boolean notify) {

    }

    @Override
    public List<BannerEntity> getBannerList() {
        return null;
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void loadBanner() {

    }

    @Override
    public void addBannerListChangedCallback(ObservableList.OnListChangedCallback callback) {

    }

    @Override
    public void addHomeEntityChangedCallback(Observable.OnPropertyChangedCallback callback) {

    }

    @Override
    public void onBannerItemClick(BannerEntity entity) {

    }

    @Override
    public List<FunctionItemEntity> getFunctionList() {
        return null;
    }

    @Override
    public void addFunctionListChangedCallback(ObservableList.OnListChangedCallback callback) {

    }

    @Override
    public void onFunctionItemClick(FunctionItemEntity entity) {

    }

    @Override
    public List<HomeListEntity> getHomeList() {
        return null;
    }

    @Override
    public void addHomeListChangedCallback(ObservableList.OnListChangedCallback callback) {

    }

    @Override
    public void loadHomeData() {

    }

    @Override
    public void changeHomeData(int position) {

    }

    @Override
    public void loadMore() {

    }
}

package com.booboomx.hotvideo.presenter.contract;

/**
 * Created by booboomx on 17/3/21.
 */

import com.booboomx.hotvideo.base.BasePresenter;
import com.booboomx.hotvideo.base.BaseView;
import com.booboomx.hotvideo.bean.VideoType;

import java.util.List;

/**
 * 评论
 */
public interface CommentContract {


    interface  View extends BaseView<Presenter>{

        boolean isActive();
        void refreshFail(String msg);
        void showContent(List<VideoType>videoTypes);
        void  showMoreContent(List<VideoType>list);



    }


    interface  Presenter extends BasePresenter{

        void  onRefresh();
        void  loadMore();
        void  setMediaId(String id);



    }
}

package com.booboomx.hotvideo.ui.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.BaseFragment;
import com.booboomx.hotvideo.bean.VideoRes;
import com.booboomx.hotvideo.presenter.VideoInfoPresenter;
import com.booboomx.hotvideo.ui.adapter.RelatedAdapter;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.utils.StringUtils;
import com.booboomx.hotvideo.widget.TextViewExpandableAnimation;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介的界面
 */
public class VideoIntroFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    EasyRecyclerView mRecyclerView;

    TextViewExpandableAnimation tvExpand;
    private View headView;

    private RelatedAdapter mAdapter;


    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_video_intro;
    }


    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);

        headView=LayoutInflater.from(mContext).inflate(R.layout.intro_header,null);
        tvExpand= ButterKnife.findById(headView,R.id.tv_expand);


        mRecyclerView.setAdapter(mAdapter=new RelatedAdapter(getContext()));
        GridLayoutManager manager=new GridLayoutManager(mContext,3);
        manager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));

        mRecyclerView.setLayoutManager(manager);

        SpaceDecoration decoration=new SpaceDecoration(ScreenUtil.dip2px(mContext,8));

        decoration.setPaddingEdgeSide(true);
        decoration.setPaddingStart(true);
        decoration.setPaddingHeaderFooter(false);


        mRecyclerView.addItemDecoration(decoration);

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                JumpUtil.go2VideoInfoActivity(getContext(),mAdapter.getItem(position));

                getActivity().finish();
            }
        });


        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return headView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });


    }


    @Subscriber(tag = VideoInfoPresenter.Refresh_Video_Info)
    public void setData(VideoRes videoRes){

        String dir="导演:"+ StringUtils.removeOtherCode(videoRes.director);
        String act="主演:"+StringUtils.removeOtherCode(videoRes.actors);
        String des=dir+"\n"+act+"\n"+"简介: "+StringUtils.removeOtherCode(videoRes.description);

        tvExpand.setText(des);
        if(videoRes.list.size()>1){
            mAdapter.addAll(videoRes.list.get(1).childList);
        }else{
            mAdapter.addAll(videoRes.list.get(0).childList);

        }


    }




    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }
}

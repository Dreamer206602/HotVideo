package com.booboomx.hotvideo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.hotvideo.utils.KL;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by booboomx on 17/2/28.
 */

/**
 *封装抽象 BaseFragment 继承 SupportFragment（封装）
 */
public abstract class BaseFragment<T extends BasePresenter> extends SupportFragment {

    public  final String TAG=getClass().getSimpleName();

    public  T mPresenter;
    public Context mContext;
    private View rootView;
    public Unbinder mUnbinder;
    public boolean isViewPrepared;//标识Fragment视图已经初始化完毕
    public  boolean hasFetchData;//标识已经触发过懒加载数据

    public String getName(){
        return  BaseFragment.class.getName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        KL.d(this.getClass(),getName()+"-------->onAttach");
        if (mContext != null) {
            this.mContext=context;
        }else{
            this.mContext=getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KL.d(this.getClass(),getName()+"-------->onCreate");
    }

    public  abstract  int getFragmentLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        KL.d(this.getClass(),getName()+"--------->onCreateView");
        if(rootView==null){
            rootView=inflater.inflate(getFragmentLayout(),container,false);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();

        if (parent != null) {
            parent.removeView(rootView);
        }

         mUnbinder = ButterKnife.bind(this, rootView);
        initView(inflater);
        EventBus.getDefault().register(this);
        setTitleHeight(rootView);
        return rootView;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KL.d(this.getClass(),getName()+"-------->onActivityCreated");
        initEvent();

    }

    @Override
    public void onStart() {
        super.onStart();
        KL.d(this.getClass(),getName()+"-------->onStart");


    }


    @Override
    public void onResume() {
        super.onResume();
        KL.d(this.getClass(),getName()+"-------->onResume");


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        KL.d(this.getClass(),getName()+"-------->onViewCreated");
        isViewPrepared=true;
        lazyFetchDataIfPrepared();


    }

    @Override
    public void onPause() {
        super.onPause();
        KL.d(this.getClass(),getName()+"-------->onPause");


    }


    @Override
    public void onStop() {
        super.onStop();
        KL.d(this.getClass(),getName()+"-------->onStop");



    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
        KL.d(this.getClass(),getName()+"-------->onDestroyView");
        // view被销毁后，将可以重新触发数据懒加载，因为在viewpager下，fragment不会再次新建并走onCreate的生命周期流程，将从onCreateView开始

        hasFetchData=false;
        isViewPrepared=false;
        mPresenter=null;



    }

    @Override
    public void onDetach() {
        super.onDetach();
        KL.d(this.getClass(),getName()+"-------->onDetach");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KL.d(this.getClass(),getName()+"-------->onDestroy");

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        KL.d(this.getClass(),getName()+"-------->isVisibleToUser= "+isVisibleToUser);

        if(isVisibleToUser){
            lazyFetchDataIfPrepared();
        }


    }

    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕

        if(getUserVisibleHint()&& !hasFetchData&& isViewPrepared){
            hasFetchData=true;
            lazyFetchData();
        }




    }

    /**
     * 懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次
     *
     * 感觉不太合理
     *
     */
    public void lazyFetchData() {
        KL.d(this.getClass(),getName()+"-------------lazyFetchData");

    }

    private void initEvent() {


    }


    private void setTitleHeight(View view) {

        if (view != null) {

        }


    }

    protected  void initView(LayoutInflater inflater){

    }


}

package com.booboomx.hotvideo.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.hotvideo.R;
import com.booboomx.hotvideo.base.RootView;
import com.booboomx.hotvideo.bean.VideoInfo;
import com.booboomx.hotvideo.bean.VideoType;
import com.booboomx.hotvideo.presenter.contract.SearchVideoListContract;
import com.booboomx.hotvideo.ui.activity.SearchActivity;
import com.booboomx.hotvideo.ui.adapter.VideoListAdapter;
import com.booboomx.hotvideo.utils.BeanUtil;
import com.booboomx.hotvideo.utils.EventUtil;
import com.booboomx.hotvideo.utils.ImageLoader;
import com.booboomx.hotvideo.utils.JumpUtil;
import com.booboomx.hotvideo.utils.Preconditions;
import com.booboomx.hotvideo.utils.ScreenUtil;
import com.booboomx.hotvideo.widget.WordWrapView;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.booboomx.hotvideo.R.id.recyclerView;

/**
 * Created by booboomx on 17/3/24.
 */

public class SearchVideoListView extends RootView<SearchVideoListContract.Presenter> implements SearchVideoListContract.View,VideoListAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    VideoInfo videoInfo;
    @BindView(recyclerView)
    EasyRecyclerView mRecyclerView;
    VideoListAdapter mAdapter;
    int pageSize = 30;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.tv_operate)
    TextView tvOperate;
    @BindView(R.id.img_search_clear)
    ImageView imgSearchClear;
    @BindView(R.id.wv_search_history)
    WordWrapView wvSearchHistory;
    @BindView(R.id.rl_his_rec)
    RelativeLayout rlHisRec;
    @BindView(R.id.img_video)
    ImageView imgVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_video1)
    ImageView imgVideo1;
    @BindView(R.id.tv_title1)
    TextView tvTitle1;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    List<VideoInfo> recommend;


    public SearchVideoListView(Context context) {
        super(context);
    }

    public SearchVideoListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void setPresenter(SearchVideoListContract.Presenter presenter) {
        mPresenter= Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {
        EventUtil.showToast(getContext(),msg);

    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void refreshFail(String msg) {


        if(!TextUtils.isEmpty(msg)){
            showError(msg);
        }
        mRecyclerView.showError();




    }

    @Override
    public void loadMoreFail(String msg) {

        if (!TextUtils.isEmpty(msg))
            showError(msg);
        mAdapter.pauseMore();


    }

    @Override
    public void shwoContent(List<VideoType> list) {

        mAdapter.clear();
        if (list != null&&list.size()<pageSize) {
            clearFooter();

        }

        mAdapter.addAll(list);
        mRecyclerView.getProgressView().setVisibility(View.GONE);
        rlHisRec.setVisibility(View.GONE);

    }

    private void clearFooter() {

        mAdapter.setMore(new View(mContext),this);
        mAdapter.setError(new View(mContext));
        mAdapter.setNoMore(new View(mContext));

    }

    @Override
    public void showMoreContent(List<VideoType> list) {
        mAdapter.addAll(list);

    }

    @Override
    public void showRecommend(List<VideoInfo> list) {
        if (list != null) {

            recommend=list;
            VideoInfo videoInfo = list.get(0);
            ImageLoader.load(getContext(),videoInfo.pic,imgVideo);
            tvTitle.setText(videoInfo.title);

             videoInfo = list.get(1);
            ImageLoader.load(getContext(),videoInfo.pic,imgVideo1);
            tvTitle1.setText(videoInfo.title);


        }else{
            llRecommend.setVisibility(GONE);
        }

    }

    @Override
    protected void getLayout() {

        inflate(getContext(), R.layout.activity_search_view,this);

    }

    @Override
    protected void initView() {

        mRecyclerView.setAdapterWithProgress(mAdapter=new VideoListAdapter(getContext()));
        mRecyclerView.setErrorView(R.layout.view_error);
        mAdapter.setMore(R.layout.view_more,this);
        mAdapter.setNoMore(R.layout.view_no_more);

        GridLayoutManager manager=new GridLayoutManager(getContext(),3);

        manager.setSpanSizeLookup(mAdapter.obtainGridSpanSizeLookUp(3));
        mRecyclerView.setLayoutManager(manager);

        SpaceDecoration spaceDecoration=new SpaceDecoration(ScreenUtil.dip2px(getContext(),8));

        spaceDecoration.setPaddingEdgeSide(true);
        spaceDecoration.setPaddingStart(true);
        spaceDecoration.setPaddingHeaderFooter(false);

        mRecyclerView.addItemDecoration(spaceDecoration);

        setHistory();



    }

    private void setHistory() {


    }


    @OnClick({R.id.tv_operate, R.id.img_clear, R.id.img_search_clear, R.id.ll_reco, R.id.ll_reco1})
    public void back(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                String searchStr = edtSearch.getText().toString();
                if (!TextUtils.isEmpty(searchStr)) {
//                    SearchKey search = new SearchKey(searchStr, System.currentTimeMillis());
//                    RealmHelper.getInstance().insertSearchHistory(search);
                    search(searchStr);
                } else {
                    if (mContext instanceof SearchActivity) {
                        ((SearchActivity) mContext).finish();
                    }
                }
                break;
            case R.id.img_clear:
                edtSearch.setText("");
                break;
            case R.id.img_search_clear:
//                RealmHelper.getInstance().deleteSearchHistoryAll();
//                wvSearchHistory.removeAllViews();
                break;
            case R.id.ll_reco:
                JumpUtil.go2VideoInfoActivity(getContext(), recommend.get(0));
                break;
            case R.id.ll_reco1:
                JumpUtil.go2VideoInfoActivity(getContext(), recommend.get(1));
                break;
        }

    }

    private void search(String searchStr) {
        mRecyclerView.getProgressView().setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(VISIBLE);
        mPresenter.setSearchKey(searchStr);
        mPresenter.onRefresh();


    }

    @Override
    protected void initEvent() {

        mRecyclerView.setRefreshListener(this);
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                videoInfo= BeanUtil.VideoType2VideoInfo(mAdapter.getItem(position),videoInfo);
                JumpUtil.go2VideoInfoActivity(getContext(),videoInfo);

            }
        });


        mAdapter.setError(R.layout.view_error_footer).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.showProgress();
                onRefresh();
            }
        });


        mRecyclerView.getErrorView().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.showProgress();
                onRefresh();

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){
                    imgClear.setVisibility(View.VISIBLE);
                    tvOperate.setText("搜索");
                }else{
                    imgClear.setVisibility(View.GONE);
                    tvOperate.setText("取消");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onLoadMore() {

        mPresenter.loadMore();

    }

    @Override
    public void onRefresh() {

        mPresenter.onRefresh();

    }
}

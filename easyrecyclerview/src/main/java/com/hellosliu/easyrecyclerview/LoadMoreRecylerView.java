package com.hellosliu.easyrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;
import com.hellosliu.easyrecyclerview.weight.SampleLoadingFooter;


public class LoadMoreRecylerView extends EasyRecylerView {

    private final static String TAG = "LoadMoreRecylerView";

    private SampleLoadingFooter footerView;


    private OnRefreshListener onRefreshListener;

    public LoadMoreRecylerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecylerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setCustomerLoadFooter(View loadingView, View networkErrorView, View dataEndView){
        footerView.setView(loadingView, networkErrorView, dataEndView);
    }

    public void setSampleLoadText(String loadingText, String netWorkErrorText, String dataEndText){
        footerView.setSampleText(loadingText, netWorkErrorText, dataEndText);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private void init(){
        footerView = new SampleLoadingFooter(getContext());
        footerView.setState(SampleLoadingFooter.State.Normal);
        super.addFootView(footerView);
        addScrollListener();
    }


    @Override
    public void addFootView(View view) {

    }


    private void addScrollListener(){
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastVisibleItemPosition = 0;

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager){
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }else if(layoutManager instanceof  GridLayoutManager){
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                }

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                //Log.d(TAG, "====>visibleItemCount:" + visibleItemCount + "====>totalItemCount:" + totalItemCount
                //        + "====>lastVisibleItemPosition:" + lastVisibleItemPosition);

                if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                    if(footerView.getState() == SampleLoadingFooter.State.TheEnd)
                        return;
                    footerView.setState(SampleLoadingFooter.State.Loading);
                    footerView.setClickable(false);
                    if(null != onRefreshListener) {
                        onRefreshListener.onRefresh();
                    }
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void onRefreshComplete(){
        footerView.setState(SampleLoadingFooter.State.Normal);
        footerView.setClickable(false);
    }

    public void setDataEnd(){
        footerView.setState(SampleLoadingFooter.State.TheEnd);
        footerView.setClickable(false);
    }

    public void setNetWorkError(){
        footerView.setState(SampleLoadingFooter.State.NetWorkError);
        footerView.setOnClickListener(onFooterReloadClickListener);
        footerView.setClickable(true);
    }

    OnClickListener onFooterReloadClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null != onRefreshListener) {
                footerView.setState(SampleLoadingFooter.State.Loading);
                onRefreshListener.onReload();
            }
        }
    };

}

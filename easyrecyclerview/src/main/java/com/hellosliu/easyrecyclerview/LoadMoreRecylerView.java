package com.hellosliu.easyrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;
import com.hellosliu.easyrecyclerview.weight.LoadingFooter;

/**
 * Created by avgd on 2016/3/19.
 */
public class LoadMoreRecylerView extends EasyRecylerView {

    private final static String TAG = "LoadMoreRecylerView";

    private LoadingFooter footerView;;

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

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private void init(){
        footerView = new LoadingFooter(getContext());
        footerView.setState(LoadingFooter.State.Normal);
        super.addFootView(footerView);
        addScrollListener();
    }


    @Override
    public void addFootView(View view) {
        //super.addFootView(view);

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

                Log.d(TAG, "====>visibleItemCount:" + visibleItemCount + "====>totalItemCount:" + totalItemCount
                        + "====>lastVisibleItemPosition:" + lastVisibleItemPosition);

                if ((visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1)) {
                    Log.d(TAG, "======>正在加载");
                    //onLoadNextPage(recyclerView);
                    footerView.setState(LoadingFooter.State.Loading);
                    onRefreshListener.onRefresh();
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void onRefreshComplete(){
        footerView.setState(LoadingFooter.State.Normal);
    }


    public void setDataEnd(){
        footerView.setState(LoadingFooter.State.TheEnd);
    }

    public void setNetWorkError(){
        footerView.setState(LoadingFooter.State.NetWorkError);
        footerView.setOnClickListener(onFooterViewReloadClickListener);
    }

    OnClickListener onFooterViewReloadClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            onRefreshListener.onReload();
        }
    };




}

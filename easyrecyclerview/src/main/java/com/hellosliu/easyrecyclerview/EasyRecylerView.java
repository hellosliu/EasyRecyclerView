package com.hellosliu.easyrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.DefaultSpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;

/**
 * Created by avgd on 2016/3/18.
 */
public class EasyRecylerView extends RecyclerView {

    //private ArrayList<View> mHeaderViews = new ArrayList<>();
    //private ArrayList<View> mFootViews = new ArrayList<>();

    private View mHeaderView;
    private View mFooterView;
    private Adapter mAdapter;



    public EasyRecylerView(Context context) {
        super(context);
    }

    public EasyRecylerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyRecylerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLayoutManager(final LayoutManager layoutManager) {
        if(layoutManager instanceof GridLayoutManager){
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (mAdapter.getItemViewType(position) == EasyAdapter.TYPE_HEADER
                            || mAdapter.getItemViewType(position) == EasyAdapter.TYPE_FOOTER) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
        super.setLayoutManager(layoutManager);
    }

    public void addHeaderView(View view){

        mHeaderView = view;
        if(mAdapter != null){
            if(!(mAdapter instanceof EasyAdapter)){
                mAdapter = new EasyAdapter(mHeaderView, mFooterView, mAdapter);
            }
        }
    }

    public void addFootView(View view){
        mFooterView = view;
        if(mAdapter != null){
            if(!(mAdapter instanceof EasyAdapter)){
                mAdapter = new EasyAdapter(mHeaderView, mFooterView, mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (null == mHeaderView && null == mFooterView){
            super.setAdapter(adapter);
        }else {
            adapter = new EasyAdapter(mHeaderView, mFooterView, adapter);
            super.setAdapter(adapter);
        }

        mAdapter = adapter;
    }


}

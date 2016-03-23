package com.hellosliu.easyrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;


public class EasyRecylerView extends RecyclerView {
    private ViewInfo mHeaderViewInfo;
    private View mFooterView;
    private Adapter mAdapter;
    private ViewInfo mEmptyViewInfo;

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
        mHeaderViewInfo = new ViewInfo();
        mHeaderViewInfo.type = ViewInfo.TYPE_NORMAL;
        mHeaderViewInfo.view = view;
        if(mAdapter != null){
            if(!(mAdapter instanceof EasyAdapter)){
                mAdapter = new EasyAdapter(mHeaderViewInfo, mFooterView, mAdapter);
            }
        }
    }

    public void addFootView(View view){
        mFooterView = view;
        if(mAdapter != null){
            if(!(mAdapter instanceof EasyAdapter)){
                mAdapter = new EasyAdapter(mHeaderViewInfo, mFooterView, mAdapter);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (null == mHeaderViewInfo && null == mFooterView){
            super.setAdapter(adapter);
        }else {
            adapter = new EasyAdapter(mHeaderViewInfo, mFooterView, adapter);
            super.setAdapter(adapter);
        }

        mAdapter = adapter;
    }

    public void showEmptyView(View view){
        mEmptyViewInfo = new ViewInfo();
        mEmptyViewInfo.type = ViewInfo.TYPE_EMPTY;
        mEmptyViewInfo.view = view;

        EasyAdapter emptyAdapter = new EasyAdapter(mEmptyViewInfo, null, null);

        super.setAdapter(emptyAdapter);
    }


    public class ViewInfo{
        public static final int TYPE_NORMAL = 0;
        public static final int TYPE_EMPTY = 1;
        public View view;
        public int type;
    }
}

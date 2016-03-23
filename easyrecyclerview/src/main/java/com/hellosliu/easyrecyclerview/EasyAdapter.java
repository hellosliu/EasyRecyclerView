package com.hellosliu.easyrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class EasyAdapter extends RecyclerView.Adapter{

    private RecyclerView.Adapter mAdapter;

    private EasyRecylerView.ViewInfo mHeaderView;
    private View mFooterView;


    public static final int TYPE_HEADER = Integer.MIN_VALUE;
    public static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;

    public EasyAdapter(EasyRecylerView.ViewInfo headerView, View footerView, RecyclerView.Adapter mAdapter) {
        this.mHeaderView = headerView;
        this.mFooterView = footerView;

        this.mAdapter = mAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER){
            ViewHolder headerHolder = new ViewHolder(mHeaderView.view);
            if(mHeaderView.type == EasyRecylerView.ViewInfo.TYPE_EMPTY) {
                headerHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(parent.getWidth(), parent.getHeight()));
            }else{
                headerHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            return headerHolder;
        }else if(viewType == TYPE_FOOTER){
            ViewHolder footerHolder = new ViewHolder(mFooterView);
            footerHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(parent.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
            return footerHolder;
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int numHeader = getNumHeader();
        if(position < numHeader){
            return ;
        }

        if(mAdapter != null){
            int adjPosition = position - numHeader;
            int adapterCount = mAdapter.getItemCount();
            if(adjPosition < adapterCount){
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
    }

    private int getNumHeader(){
        return null == mHeaderView ? 0 : 1 ;
    }

    private int getNumFooter(){
        return null == mFooterView ? 0 : 1 ;
    }

    @Override
    public int getItemViewType(int position) {

        int numHeader = getNumHeader();
        if(position < numHeader){
            return TYPE_HEADER;
        }

        if(mAdapter != null){
            int adjPosition = position - numHeader;
            int adapterCount = mAdapter.getItemCount();
            if(adjPosition < adapterCount){
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return TYPE_FOOTER;
    }

    @Override
    public long getItemId(int position) {

        int numHeader = getNumHeader();
        if(position < numHeader){
            return TYPE_HEADER;
        }

        if(mAdapter != null){
            int adjPosition = position - numHeader;
            int adapterCount = mAdapter.getItemCount();
            if(adjPosition < adapterCount){
                return mAdapter.getItemId(adjPosition);
            }
        }

        return TYPE_FOOTER;
    }

    @Override
    public int getItemCount() {
        if(null != mAdapter){
            return getNumHeader() + getNumFooter() + mAdapter.getItemCount();
        }else {
            return getNumHeader() + getNumFooter();
        }
    }


    private static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

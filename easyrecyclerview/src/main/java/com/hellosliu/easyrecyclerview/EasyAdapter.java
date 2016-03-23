package com.hellosliu.easyrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by avgd on 2016/3/18.
 */
public class EasyAdapter extends RecyclerView.Adapter{

    private RecyclerView.Adapter mAdapter;
    //private ArrayList<View> mHeaderViews;
    //private ArrayList<View> mFooterView;

    private View mHeaderView;
    private View mFooterView;

    //static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<View>();

    private int mCurrentPosition;


    public static final int TYPE_HEADER = Integer.MIN_VALUE;
    public static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;
    private static final int TYPE_ADAPTEE_OFFSET = 2;

    public EasyAdapter(View headerView, View footerView, RecyclerView.Adapter mAdapter) {


        this.mHeaderView = headerView;
        this.mFooterView = footerView;

        this.mAdapter = mAdapter;

        /*
        this.mHeaderViews = mHeaderViews;
        if(mHeaderViews == null){
            this.mHeaderViews = EMPTY_INFO_LIST;
        }else {
            this.mHeaderViews = mHeaderViews;
        }
        if(mFooterView == null){
            this.mFooterView = EMPTY_INFO_LIST;
        }else {
            this.mFooterView = mFooterView;
        }
        this.mAdapter = mAdapter;
        */
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_HEADER){
            return new ViewHolder(mHeaderView);
        }else if(viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }

        return mAdapter.onCreateViewHolder(parent, viewType);

        //if(viewType == )
        /*
        if(viewType == RecyclerView.INVALID_TYPE){
            return new HeaderViewHolder(mHeaderViews.get(0));
        }else if(viewType == RecyclerView.INVALID_TYPE -1){
            return new HeaderViewHolder(mHeaderViews.get(0));
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
        */
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

        /*int numHeaders = getHeadersCount();
        if(position < numHeaders){
            return;
        }

        int adjPostion = position - numHeaders;

        int adapterCount = 0;

        if(null != mAdapter){
            adapterCount = mAdapter.getItemCount();
            if(adjPostion < adapterCount){
                mAdapter.onBindViewHolder(holder, adjPostion);
                return;
            }
        }*/
    }

    private int getNumHeader(){
        return null == mHeaderView ? 0 : 1 ;
    }

    private int getNumFooter(){
        return null == mFooterView ? 0 : 1 ;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);

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

        /*
        mCurrentPosition = position;
        int numHeaders = getHeadersCount();

        if(position < numHeaders){
            return RecyclerView.INVALID_TYPE;
        }

        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if(null != mAdapter){
            adapterCount = mAdapter.getItemCount();
            if(adjPosition < adapterCount){
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        return RecyclerView.INVALID_TYPE - 1;
        */
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

        /*
        int numHeaders = getHeadersCount();
        if(mAdapter != null && position >= numHeaders){
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if(adjPosition < adapterCount){
                return mAdapter.getItemId(adjPosition);
            }
        }
        return -1;
        */
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

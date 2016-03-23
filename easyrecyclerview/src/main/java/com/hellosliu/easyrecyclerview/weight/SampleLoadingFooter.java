package com.hellosliu.easyrecyclerview.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.R;
import com.hellosliu.easyrecyclerview.utils.StringUtils;

/**
 * Created by avgd on 2016/3/22.
 */
public class SampleLoadingFooter extends RelativeLayout {

    private ViewGroup mLoadingFooter;
    private View mLoadingView;
    private View mNetworkErrorView;
    private View mTheEndView;

    private TextView mLoadingTextView;
    private TextView mNetworkErrorTextView;
    private TextView mTheEndTextView;

    private String mLoadingText;
    private String mNetWorkErrorText;
    private String mDataEndText;

    private LayoutInflater layoutInflater;
    private State mState = State.Normal;


    public SampleLoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public SampleLoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SampleLoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setView(View loadingView, View netWorkErrorView, View endView){
        this.mLoadingView = loadingView;
        this.mNetworkErrorView = netWorkErrorView;
        this.mTheEndView = endView;
    }

    public void setSampleText(String loadingText, String netWorkErrorText, String dataEndText){
        this.mLoadingText = loadingText;
        this.mNetWorkErrorText = netWorkErrorText;
        this.mDataEndText = dataEndText;
    }




    private void init(Context context){
        inflate(context, R.layout.sample_loading_footer, this);
        mLoadingFooter = (ViewGroup) findViewById(R.id.sample_loading_view);
        layoutInflater = LayoutInflater.from(context);
    }

    public State getState() {
        return mState;
    }

    public void setState(State state){
        if(mState == state){
            return;
        }
        mState = state;

        switch (state){
            case Normal:
                setNormalState();
                break;
            case Loading:
                setLoadingState();
                break;
            case TheEnd:
                setEndingState();
                break;
            case NetWorkError:
                setNetWorkErrorState();
                break;
        }
    }

    private void setNormalState(){
        mLoadingFooter.removeAllViews();
    }

    private void setLoadingState(){
        mLoadingFooter.removeAllViews();
        if(null == mLoadingView){
            mLoadingView = layoutInflater.inflate(R.layout.sample_footer_loading, null);
            if(StringUtils.isNotBlank(mLoadingText)){
                mLoadingTextView = (TextView) mLoadingView.findViewById(R.id.loading_text);
                mLoadingTextView.setText(mLoadingText);
            }
        }
        mLoadingFooter.addView(mLoadingView);
    }

    private void setEndingState(){
        mLoadingFooter.removeAllViews();
        if(null == mTheEndView){
            mTheEndView = layoutInflater.inflate(R.layout.sample_footer_data_end, null);
            if(StringUtils.isNotBlank(mDataEndText)){
                mTheEndTextView = (TextView) mTheEndView.findViewById(R.id.loading_text);
                mTheEndTextView.setText(mDataEndText);
            }
        }
        mLoadingFooter.addView(mTheEndView);
    }

    private void setNetWorkErrorState(){
        mLoadingFooter.removeAllViews();
        if(null == mNetworkErrorView){
            mNetworkErrorView = layoutInflater.inflate(R.layout.sample_footer_network_error, null);
            if(StringUtils.isNotBlank(mNetWorkErrorText)){
                mNetworkErrorTextView = (TextView) mNetworkErrorView.findViewById(R.id.loading_text);
                mNetworkErrorTextView.setText(mNetWorkErrorText);
            }
        }
        mLoadingFooter.addView(mNetworkErrorView);
    }



    public static enum State {
        Normal/**正常*/, TheEnd/**加载到最底了*/, Loading/**加载中..*/, NetWorkError/**网络异常*/
    }
}

package com.hellosliu.easyrecyclerview_master;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.EasyRecylerView;
import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadMoreRecylerView recylerView;

    private Context mContext;

    private PreviewHandler mHandler = new PreviewHandler();

    private int count = 0;

    private List<String> names = new ArrayList<String>();
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = new ListView(getApplicationContext());

        listView.addHeaderView(new Button(getApplication()));


        recylerView = (LoadMoreRecylerView) findViewById(R.id.recyle_view);
        //recylerView.setLayoutManager(new LinearLayoutManager(mContext));
        recylerView.setLayoutManager(new GridLayoutManager(this, 2));

        TextView textView = new TextView(this);
        textView.setText("this is head");

        TextView textView2 = new TextView(this);
        textView2.setText("this is footer");

        recylerView.addHeaderView(textView);
        recylerView.addFootView(textView2);

        names = new ArrayList<String>();
        for (int i = 1; i<=20; i++){
            names.add("item:" + i);
            count = i;
        }

        adapter = new TestAdapter(this, names);

        recylerView.setAdapter(adapter);

        recylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }

            @Override
            public void onReload() {
                Log.d("TAG", "=======>reload!!!");
                requestData();
            }
        });
    }


    private static class PreviewHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {


            /*switch (msg.what) {
                case -1:
                    int currentSize = activity.mDataAdapter.getItemCount();

                    //模拟组装10个数据

                    for (int i = 0; i < 10; i++) {

                    }

                    activity.addItems(newList);
                    RecyclerViewStateUtils.setFooterViewState(activity.mRecyclerView, LoadingFooter.State.Normal);
                    break;
                case -2:
                    activity.notifyDataSetChanged();
                    break;
                case -3:
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.mRecyclerView, REQUEST_COUNT, LoadingFooter.State.NetWorkError, activity.mFooterClick);
                    break;
            }*/
        }
    }


    private void requestData() {

        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        if(count > 60){
                            recylerView.setNetWorkError();
                            return;
                        }

                        for (int i = 0; i < 10; i++) {
                            count ++;
                            names.add("item:" + count);
                        }

                        adapter.notifyDataSetChanged();
                        recylerView.onRefreshComplete();
                    }
                });



                //模拟一下网络请求失败的情况
                /*if(NetworkUtils.isNetAvailable(EndlessLinearLayoutActivity.this)) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }*/
            }
        }.start();
    }

}

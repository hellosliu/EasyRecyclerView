package com.hellosliu.easyrecyclerview_master;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hellosliu.easyrecyclerview.LoadMoreRecylerView;
import com.hellosliu.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class LoadingRecyclerViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LoadMoreRecylerView recylerView;
    private List<String> items = new ArrayList<String>();
    private MyAdapter adapter;

    private LayoutInflater layoutInflater;

    private int count = 0;
    private boolean temState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        layoutInflater = LayoutInflater.from(this);

        init();
    }

    private void init() {
        initToolBar();
        for(int i=0; i<20; i++){
            items.add("item:" + i);
            count = i;
        }
        setRecycleView();
    }

    private void setRecycleView(){

        View header = layoutInflater.inflate(R.layout.view_customer_header, null);

        recylerView = (LoadMoreRecylerView)findViewById(R.id.recycleview_loading);

        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.addHeaderView(header);
        //设置加载，网络异常，数据到底文字
        //setSampleLoadText();
        //自定义加载，网络异常，数据到底 显示view
        //setCustomerLoadFoot();

        recylerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreData();
            }

            @Override
            public void onReload() {
                getMoreData();
            }
        });

        adapter = new MyAdapter(this, items);
        recylerView.setAdapter(adapter);
    }

    private void setSampleLoadText(){
        //设置加载，网络异常，数据到底文字
        recylerView.setSampleLoadText("Loading...", "NetWork Error", "Data End");
    }

    private void setCustomerLoadFoot(){
        TextView loadingView = new TextView(this);
        loadingView.setGravity(Gravity.CENTER);
        loadingView.setText("Customer Loading...");

        TextView networkErrorView = new TextView(this);
        networkErrorView.setGravity(Gravity.CENTER);
        networkErrorView.setText("Customer NetWork Error");

        TextView dataEndView = new TextView(this);
        dataEndView.setGravity(Gravity.CENTER);
        dataEndView.setText("Customer Data End");

        //自定义加载，网络异常，数据到底 显示view
        recylerView.setCustomerLoadFooter(loadingView, networkErrorView, dataEndView);
    }

    private void getMoreData(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable(){
                    @Override
                    public void run() {
                        if(count > 19 && temState){
                            temState = false;
                            recylerView.setNetWorkError();
                            return;
                        }
                        if(count > 29){
                            recylerView.setDataEnd();
                            return;
                        }
                        for (int i = 0; i < 10; i++) {
                            count ++;
                            items.add("item:" + count);

                        }
                        adapter.notifyDataSetChanged();
                        recylerView.onRefreshComplete();
                    }
                });
            }
        }.start();
    }

    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_loading);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingRecyclerViewActivity.this.finish();
            }
        });
    }

    private void showEmptyView(){
        View view = layoutInflater.inflate(R.layout.view_customer_empty, null);
        recylerView.showEmptyView(view);
    }

    private void showNetWorkErrorView(){
        View view = layoutInflater.inflate(R.layout.view_customer_network_error, null);
        recylerView.showEmptyView(view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.switch_grid:
                recylerView.setLayoutManager(new GridLayoutManager(this, 2));
                return true;
            case R.id.show_empty:
                showEmptyView();
                return true;
            case R.id.show_networkerror:
                showNetWorkErrorView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

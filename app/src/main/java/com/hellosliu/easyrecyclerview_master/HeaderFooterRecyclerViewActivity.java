package com.hellosliu.easyrecyclerview_master;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.hellosliu.easyrecyclerview.EasyRecylerView;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterRecyclerViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EasyRecylerView recylerView;
    private List<String> items = new ArrayList<String>();
    private MyAdapter adapter;

    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);
        layoutInflater = LayoutInflater.from(this);

        init();

    }

    private void init() {
        initToolBar();
        for(int i=0; i<7; i++){
            items.add("item:" + i);
        }

        setRecycleView();
    }

    private void setRecycleView(){


        View header = layoutInflater.inflate(R.layout.view_customer_header, null);
        View footer = layoutInflater.inflate(R.layout.view_customer_footer, null);

        recylerView = (EasyRecylerView)findViewById(R.id.recycleview_header_footer);

        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.addHeaderView(header);
        recylerView.addFootView(footer);

        adapter = new MyAdapter(this, items);
        recylerView.setAdapter(adapter);

    }


    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_header_footer);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeaderFooterRecyclerViewActivity.this.finish();
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
        inflater.inflate(R.menu.menu_header_footer, menu);
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

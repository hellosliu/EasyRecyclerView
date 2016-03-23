package com.hellosliu.easyrecyclerview_master;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Button headerFooterButton;
    private Button loadingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initToolBar();
        headerFooterButton = (Button) findViewById(R.id.btn_header_fooder);
        loadingButton = (Button) findViewById(R.id.btn_loading);

        headerFooterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, HeaderFooterRecyclerViewActivity.class);
                startActivity(mIntent);
            }
        });
        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, LoadingRecyclerViewActivity.class);
                startActivity(mIntent);
            }
        });
    }

    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
    }
}

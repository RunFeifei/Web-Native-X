package com.example.root.deep;

import android.content.Intent;
import android.os.Bundle;

import com.example.root.deep.base.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.textDeep)
    public void textDeep() {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}

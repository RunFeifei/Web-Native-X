package com.example.root.deep;

import android.content.Intent;
import android.os.Bundle;

import com.example.root.deep.base.BaseActivity;
import com.example.root.deep.deeplink.WebViewActivity;
import com.example.root.deep.jsbridge.BridgeWebActivity;
import com.example.root.deep.jsroad.RoadWebActivity;

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

    @OnClick(R.id.textBridge)
    public void onViewClicked() {
        startActivity(new Intent(this, BridgeWebActivity.class));
    }

    @OnClick(R.id.textRoad)
    public void onRoadClicked() {
        startActivity(new Intent(this, RoadWebActivity.class));
    }
}

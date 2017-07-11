package com.example.root;

import android.content.Intent;
import android.os.Bundle;

import com.example.root.base.BaseActivity;
import com.example.root.deep.R;
import com.example.root.deeplink.WebViewActivity;
import com.example.root.jsbridge.BridgeWebActivity;
import com.example.root.jsroad.RoadWebActivity;
import com.example.root.router.RouterActivity;
import com.fei.processor.annotation.Router;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("MainActivity");
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

    @OnClick(R.id.textTest)
    public void onTestClicked() {
        startActivity(new Intent(this, RouterActivity.class));
    }
}

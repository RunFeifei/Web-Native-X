package com.example.root.deep;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.root.deep.base.BaseActivity;
import com.github.lzyzsd.jsbridge.BridgeWebView;

import butterknife.BindView;
import butterknife.OnClick;

public class BridgeWebActivity extends BaseActivity {


    @BindView(R.id.webView)
    BridgeWebView webView;
    @BindView(R.id.textCallJS)
    TextView textCallJS;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bridge_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.btnGoBack, R.id.btnGoForward, R.id.btnRefresh, R.id.btnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoBack:
                webView.goBack();
                break;
            case R.id.btnGoForward:
                webView.goForward();
                break;
            case R.id.btnRefresh:
                webView.reload();
                break;
            case R.id.btnStop:
                webView.stopLoading();
                break;
        }
    }
}

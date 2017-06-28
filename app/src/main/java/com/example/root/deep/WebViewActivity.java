package com.example.root.deep;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.root.deep.base.BaseActivity;
import com.example.root.deep.deeplink.DeepLinkHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btnGoBack)
    TextView btnGoBack;
    @BindView(R.id.btnGoForward)
    TextView btnGoForward;
    @BindView(R.id.btnRefresh)
    TextView btnRefresh;
    @BindView(R.id.btnStop)
    TextView btnStop;

    private WebViewClient webViewClient;

    private void initWebClient() {
        webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (DeepLinkHelper.checkScheme(url)) {
                    DeepLinkHelper.jump2nativeDispacher(WebViewActivity.this, url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initWebClient();
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("file:///android_asset/index.html");
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

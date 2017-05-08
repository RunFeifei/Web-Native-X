package com.example.root.deep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dianrong.android.deeplink.DeepLink;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(getClient());
        webView.loadUrl("file:///android_asset/index.html");
    }

    WebViewClient getClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (DeepLink.check(WebViewActivity.this, url, "")) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };
    }

}

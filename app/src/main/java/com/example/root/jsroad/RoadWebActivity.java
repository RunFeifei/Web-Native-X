package com.example.root.jsroad;

import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.root.deep.R;
import com.example.root.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PengFeifei on 17-7-3.
 */

public class RoadWebActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_road_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("RoadWebActivity");
        webView.setWebChromeClient(getChromeClient());
        webView.setWebViewClient(getWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/road.html");
        JSRoadHelper.bindMethods(this);
    }

    private WebChromeClient getChromeClient() {
        return new WebChromeClient() {

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                result.confirm(JSRoadHelper.invokeNativeMethod(view, message));
                return true;
            }
        };
    }

    private WebViewClient getWebClient() {
        return new WebViewClient();
    }

    @JSRoadMap(map = true)
    public String methodName(String content) {
        toast(content);
        return "Hello Js....";
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

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

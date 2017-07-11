package com.example.root.deeplink;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.base.BaseActivity;
import com.example.root.deep.R;

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
    @BindView(R.id.textCallJS1)
    TextView textCallJS1;
    @BindView(R.id.textCallJS2)
    TextView textCallJS2;
    @BindView(R.id.textCallJS3)
    TextView textCallJS3;

    private WebViewClient getWebClient() {
        return new WebViewClient() {
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

    private WebChromeClient getChomeClient() {
        return new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("dianrong")) {
                    result.confirm(uri.getHost());
                    return true;
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        };
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("WebViewActivity");
        webView.setWebViewClient(getWebClient());
        webView.setWebChromeClient(getChomeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new JsInteraction(), "JsInterface");
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

    @OnClick({R.id.textCallJS1, R.id.textCallJS2, R.id.textCallJS3})
    public void onNativeClicked(View view) {
        switch (view.getId()) {
            case R.id.textCallJS1: {
                webView.loadUrl("javascript:onNativeClicked('HelloJs')");
                break;
            }
            case R.id.textCallJS2: {
                webView.evaluateJavascript("onNativeClicked2('HelloJs')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(WebViewActivity.this, "Native get-->" + value, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            case R.id.textCallJS3: {
                new RequestMonitor().execute();
                break;
            }
        }


    }

    private class JsInteraction {

        @JavascriptInterface
        public void toDestPage(final String param) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WebViewActivity.this, "Native get-->" + param, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class RequestMonitor extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return "waked up";
            }
            return "HelloJs";
        }

        @Override
        protected void onPostExecute(String text) {
            super.onPostExecute(text);
            webView.loadUrl("javascript:onNativeClicked('" + text + "')");
        }
    }

}
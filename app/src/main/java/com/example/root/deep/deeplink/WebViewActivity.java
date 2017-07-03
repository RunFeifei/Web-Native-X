package com.example.root.deep.deeplink;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.root.deep.DestActivity;
import com.example.root.deep.R;
import com.example.root.deep.base.BaseActivity;

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
        webView.setWebViewClient(getWebClient());
        webView.setWebChromeClient(getChomeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        webView.addJavascriptInterface(new JsInteraction(), "Hello_Js");
    }

    @OnClick(R.id.textCallJS)
    public void onNativeBtnClick() {
        /*webView.loadUrl("javascript:onNativeClicked('HelloJs')");

        webView.evaluateJavascript("onNativeClicked2('HelloJs')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Toast.makeText(WebViewActivity.this, value, Toast.LENGTH_SHORT).show();
            }
        });*/

        new RequestMonitor().execute();


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

    private class JsInteraction {

        @JavascriptInterface
        public void toDestPage(final String param) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WebViewActivity.this, DestActivity.class);
                    intent.putExtra("data", param);
                    startActivity(intent);
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

            return "Hello Dianrong";
        }

        @Override
        protected void onPostExecute(String text) {
            super.onPostExecute(text);
            webView.loadUrl("javascript:onNativeClicked('" + text + "')");
        }
    }

}

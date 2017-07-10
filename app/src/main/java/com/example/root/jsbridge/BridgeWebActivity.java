package com.example.root.jsbridge;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.example.root.deep.R;
import com.example.root.base.BaseActivity;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;

import butterknife.BindView;
import butterknife.OnClick;

public class BridgeWebActivity extends BaseActivity {

    @BindView(R.id.webView)
    BridgeWebView webView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_bridge_web;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        webView.setDefaultHandler(getBridgeHandler());
        webView.setWebChromeClient(getChromeClient());
        webView.setWebViewClient(getBridgeClient());
        webView.loadUrl("file:///android_asset/bridge.html");
        //native注册callNative Js主动调用,并返回给JS
        regisHandler();
    }

    private BridgeHandler getBridgeHandler() {
        return new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                toast(data);
                //测试是否可以异步
                function.onCallBack("Hello JS");
            }
        };
    }


    private WebChromeClient getChromeClient() {
        return new WebChromeClient() {
        };
    }

    private BridgeWebViewClient getBridgeClient() {
        return new BridgeWebViewClient(webView) {
        };
    }

    private void regisHandler() {
        webView.registerHandler("callNative", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                toast("native get-->" + data);
                function.onCallBack("Hello Js");
            }
        });
    }


    @OnClick(R.id.textCallJS)
    public void onViewClicked() {
       /* webView.send("Hello JS", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                toast(data);
            }
        });*/

        //callJs Java主动调用并获得返回值
        webView.callHandler("callJs", "Hello Js", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                toast("java get->" + data);
            }
        });
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

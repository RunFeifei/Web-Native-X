package com.example.root.deep.deeplink;

import android.content.Intent;
import android.os.Bundle;

import com.example.root.deep.DestActivity;
import com.example.root.deep.base.BaseActivity;

import java.util.HashMap;

/**
 * 假如我们从浏览器打开了应用，按下HOME键，然后又从桌面点击了应用图标，这时候会发生什么？
 * 假如应用之前已经打开了某个页面，然后我们又从浏览器重新打开了应用，这时候按返回键，我们还能回到之前打开的页面么？
 */
public class DispatcherActivity extends BaseActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
        String url = getIntent().getDataString();
        HashMap<String, String> map = DeepLinkHelper.parseUrl2Map(url);
        if (map == null) {
            finish();
            return;
        }
        DeepLinkHelper.invokebyAction(this, map);
    }


    @LinkMap(action = "ACTION_JUMP")
    public void toDestActivity(HashMap<String, String> params) {
        Intent intent = new Intent(this, DestActivity.class);
        intent.putExtra(DeepLinkHelper.PARAMS, params);
        startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return 0;
    }
}

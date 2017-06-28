package com.example.root.deep.deeplink;

import android.content.Intent;
import android.os.Bundle;

import com.example.root.deep.DestActivity;
import com.example.root.deep.base.BaseActivity;

import java.util.HashMap;

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

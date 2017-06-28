package com.example.root.deep;

import android.os.Bundle;
import android.widget.TextView;

import com.example.root.deep.base.BaseActivity;
import com.example.root.deep.deeplink.DeepLinkHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;

public class DestActivity extends BaseActivity {


    @BindView(R.id.textTip)
    TextView textTip;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dest;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        HashMap<String, String> map = (HashMap<String, String>) getIntent().getSerializableExtra(DeepLinkHelper.PARAMS);
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            textTip.append(entry.getKey() + "-->" + entry.getValue());
            textTip.append("\n");
        }
    }

}

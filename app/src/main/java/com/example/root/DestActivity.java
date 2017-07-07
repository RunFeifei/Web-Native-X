package com.example.root;

import android.os.Bundle;
import android.widget.TextView;

import com.example.root.deep.R;
import com.example.root.base.BaseActivity;
import com.example.root.deeplink.DeepLinkHelper;
import com.fei.processor.annotation.Router;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
@Router(action = "dest",module = "app")
public class DestActivity extends BaseActivity {


    @BindView(R.id.textTip)
    TextView textTip;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dest;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setJsText();
        setTipText();
    }

    private void setTipText() {
        HashMap<String, String> map = (HashMap<String, String>) getIntent().getSerializableExtra(DeepLinkHelper.PARAMS);
        if (map == null) {
            return;
        }
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            textTip.append(entry.getKey() + "-->" + entry.getValue());
            textTip.append("\n");
        }
    }

    private void setJsText() {
        textTip.setText(getIntent().getStringExtra("data"));
    }

}

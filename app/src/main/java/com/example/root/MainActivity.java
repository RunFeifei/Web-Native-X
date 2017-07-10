package com.example.root;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.base.BaseActivity;
import com.example.root.deep.R;
import com.example.root.deeplink.WebViewActivity;
import com.example.root.jsbridge.BridgeWebActivity;
import com.example.root.jsroad.RoadWebActivity;
import com.example.root.router.AppRoutes;
import com.fei.processor.annotation.Router;
import com.fei.root.module.ModuleActivity;
import com.fei.root.router.RouterHelper;

import butterknife.BindView;
import butterknife.OnClick;

@Router(action = "main")
public class MainActivity extends BaseActivity {

    @BindView(R.id.textTest)
    TextView textText;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra(RouterHelper.BUNDLE);
        if (bundle != null) {
            textText.setText(bundle.toString());
        }
    }

    @OnClick(R.id.textDeep)
    public void textDeep() {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    @OnClick(R.id.textBridge)
    public void onViewClicked() {
        startActivity(new Intent(this, BridgeWebActivity.class));
    }

    @OnClick(R.id.textRoad)
    public void onRoadClicked() {
        startActivity(new Intent(this, RoadWebActivity.class));
    }

    @OnClick(R.id.textModule)
    public void onModuleClicked() {
        startActivity(new Intent(this, ModuleActivity.class));
    }

    @OnClick(R.id.textTest)
    public void onTestClicked() {
        new RouterHelper().create(this, AppRoutes.class).toModule1("tom", 12);
    }
}

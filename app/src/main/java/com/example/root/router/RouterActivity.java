package com.example.root.router;

import android.os.Bundle;
import android.widget.TextView;

import com.example.root.base.BaseActivity;
import com.example.root.deep.R;
import com.fei.processor.annotation.Router;
import com.fei.root.router.RouterHelper;

import butterknife.BindView;
import butterknife.OnClick;

@Router(action = "router")
public class RouterActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_router;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("RouterActivity");
        Bundle bundle = getIntent().getBundleExtra(RouterHelper.BUNDLE);
        if (bundle != null) {
            textView.setText(bundle.getString("name")+"-->"+bundle.getInt("age"));
        }
    }


    @OnClick(R.id.textView)
    public void onViewClicked() {
        new RouterHelper().create(this, AppRoutes.class).toModule1("tom", 12);
    }
}

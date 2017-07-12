package com.fei.root.module;

import android.os.Bundle;
import android.widget.TextView;

import com.fei.processor.annotation.Router;
import com.fei.root.module.viewbinder.Binder;
import com.fei.root.router.RouterHelper;

@Router(action = "module2", module = "module")
public class Module2Activity extends BaseActivity {

    @Binder
    private TextView text1;
    @Binder
    private TextView text2;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_module2;
    }

    @Override
    protected void init() {
        setTitle("Module2Activity");
        Bundle bundle = getIntent().getBundleExtra(RouterHelper.BUNDLE);
        text1.setText(bundle.getString("name"));
        text2.setText(Integer.toString(bundle.getInt("age")));
    }
}


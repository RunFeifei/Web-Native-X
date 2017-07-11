package com.fei.root.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fei.processor.annotation.Router;
import com.fei.root.router.RouterHelper;

@Router(action = "module2", module = "module")
public class Module2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module2);
        setTitle("Module2Activity");
        Bundle bundle = getIntent().getBundleExtra(RouterHelper.BUNDLE);
        ((TextView) findViewById(R.id.text1)).setText(bundle.getString("name"));
        ((TextView) findViewById(R.id.text2)).setText(Integer.toString(bundle.getInt("age")));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


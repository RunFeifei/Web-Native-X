package com.fei.root.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.processor.annotation.Router;
import com.fei.root.router.RouterHelper;


@Router(action = "module1", module = "module")
public class ModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
    }

    public void onModuleClick(View v) {
        new RouterHelper().create(this, ModuleRoutes.class).toMain("lucy", 13);
    }

}

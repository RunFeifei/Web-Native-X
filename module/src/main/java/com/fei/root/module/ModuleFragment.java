package com.fei.root.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fei.processor.annotation.Router;

@Router(param = "moduleFragment",module = "module")
public class ModuleFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_fragment);
    }
}

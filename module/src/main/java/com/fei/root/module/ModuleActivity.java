package com.fei.root.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fei.processor.annotation.Router;

import java.util.HashMap;


@Router(action = "moduleActivity",module = "module")
public class ModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
    }


    public static HashMap<String, Class<?>> getRouteMap() {
        HashMap<String,Class<?>> map = new HashMap<>();
        map.put("moduleFragment",ModuleFragment.class);
        map.put("moduleActivity",ModuleActivity.class);
        return map;
    }
}

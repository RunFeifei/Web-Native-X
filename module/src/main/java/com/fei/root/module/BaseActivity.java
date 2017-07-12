package com.fei.root.module;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fei.root.module.viewbinder.ViewBinder;

/**
 * Created by PengFeifei on 17-6-28.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            View content = getLayoutInflater().inflate(getContentViewId(), null);
            if (content != null) {
                setContentView(content);
                ViewBinder.bindViews(this,content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }



    @LayoutRes
    protected abstract int getContentViewId();

    protected abstract void init();


}

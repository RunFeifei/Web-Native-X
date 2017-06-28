package com.example.root.deep.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PengFeifei on 17-6-28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder butterKinfeBinder;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View content = getLayoutInflater().inflate(getContentViewId(), null);
        setContentView(content);
        butterKinfeBinder = ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        butterKinfeBinder.unbind();
    }


    @LayoutRes
    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

}

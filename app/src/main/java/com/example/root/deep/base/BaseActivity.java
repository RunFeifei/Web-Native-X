package com.example.root.deep.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
        try {
            View content = getLayoutInflater().inflate(getContentViewId(), null);
            if (content != null) {
                setContentView(content);
                butterKinfeBinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (butterKinfeBinder != null) {
            butterKinfeBinder.unbind();
        }
    }


    @LayoutRes
    protected abstract int getContentViewId();

    protected abstract void init(Bundle savedInstanceState);

}

package com.example.root.router;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by PengFeifei on 17-7-6.
 */
public class RouterHelper {

    private AppCompatActivity activity;
    private Fragment fragment;

    private String action;
    private Bundle bundle;
    private Intercept intercept;

    public RouterHelper() {
        this.bundle = new Bundle();
    }


   /* public RouterHelper bind(AppCompatActivity activity) {
        this.activity = activity;
        return this;
    }

    public RouterHelper bind(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }*/

    public RouterHelper bindAction(String action) {
        this.action = action;
        return this;
    }

    public RouterHelper bindValue(String key, Object value) {
        if (value instanceof String) {
            bundle.putString(key, (String) value);

        } else if (value instanceof Integer) {
            bundle.putInt(key, (int) value);

        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);

        }
        /*and else ...*/
        return this;
    }

    public void bindIntercept(Intercept intercept) {
        this.intercept = intercept;
    }


    public void doJump(Context context) {
        if (action == null) {
            throw new RuntimeException("action not assigned");
        }



    }


    public interface Intercept {
        boolean onIntercept();
    }

}

package com.fei.root.router;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fei.processor.ClassProcessor;
import com.fei.processor.RouteMap;

/**
 * Created by PengFeifei on 17-7-6.
 */
public class RouterHelper {

    private static final String TAG = RouterHelper.class.getSimpleName();
    public static final String BUNDLE = "bundle";


    private AppCompatActivity activity;
    private Fragment fragment;

    private String action;
    private int flags;
    private Bundle bundle;
    private Intercept intercept;

    public RouterHelper() {
    }


    public RouterHelper bind(AppCompatActivity activity) {
        this.activity = activity;
        return this;
    }

    public RouterHelper bind(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public RouterHelper bindAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * 只支持执行一次
     */
    public RouterHelper bindFlag(int flags) {
        this.flags = flags;
        return this;
    }

    public RouterHelper bindValue(String key, Object value) {
        this.bundle = new Bundle();
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


    public void doJump() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        if (fragment != null) {
            fragment.startActivity(intent);
            return;
        }
        activity.startActivity(intent);
    }

    public void doJump(int requestCode) {
        Intent intent = getIntent();
        if (intent != null) {
            return;
        }
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
            return;
        }
        activity.startActivityForResult(intent, requestCode);
    }


    public interface Intercept {
        boolean onIntercept();
    }

    private String[] getRouteMapClassNames() {

        final String[] modules = AppContext.getInstance().getString(R.string.modules).split(",");
        final String classFormat = "%1sRouteMap";
        final int len = modules.length;

        String[] routeMapClass = new String[len];
        for (int i = 0; i < len; i++) {
            String name = modules[i].substring(0, 1).toUpperCase() + modules[i].substring(1);
            routeMapClass[i] = String.format(classFormat, name);
        }
        return routeMapClass;
    }

    private Class<?> getTargetClass(String action) {
        if (action == null) {
            throw new RuntimeException("action not assigned");
        }
        String[] routeMapClassNames = getRouteMapClassNames();

        for (String className : routeMapClassNames) {
            try {
                Class<?> routeMapClass = Class.forName(ClassProcessor.PACKAGE_NAME + "." + className);
                RouteMap routeMap = (RouteMap) routeMapClass.newInstance();
                Class<?> target = routeMap.getRouteMap().get(action);
                if (target != null) {
                    return routeMap.getRouteMap().get(action);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                continue;
            }
        }
        return null;
    }

    private Context getContext() {
        if (fragment == null && activity == null) {
            throw new RuntimeException("not bind yet");
        }
        if (fragment != null && activity != null) {
            throw new RuntimeException("do not bind both fragment & activity");
        }
        if (fragment != null) {
            return fragment.getContext();
        }
        return activity;
    }

    private Intent getIntent() {
        Class<?> targetClass = getTargetClass(action);
        if (targetClass == null) {
            Log.e(TAG, "could not match TargetClass");
            return null;
        }
        if (Service.class.isAssignableFrom(targetClass)) {
            // TODO: 17-7-7 暂不支持到Service的跳转
            return null;
        }
        if (Fragment.class.isAssignableFrom(targetClass)) {
            // TODO: 17-7-7 暂不支持到fragment的跳转
            return null;
        }
        if (!Activity.class.isAssignableFrom(targetClass)) {
            throw new RuntimeException("target class not activity");
        }
        Intent intent = new Intent(getContext(), targetClass);
        intent.putExtra(BUNDLE, bundle);
        intent.setFlags(flags);
        return intent;
    }

}

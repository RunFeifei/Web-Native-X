package com.example.root;

import android.app.Application;

/**
 * Created by PengFeifei on 17-7-7.
 */

public class WebativeApp extends Application {

    private static WebativeApp webativeApp;

    @Override
    public void onCreate() {
        super.onCreate();
        webativeApp = this;
    }

    public synchronized static WebativeApp getInstance() {
        return webativeApp;
    }


}

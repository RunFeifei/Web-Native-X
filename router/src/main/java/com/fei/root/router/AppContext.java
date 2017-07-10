package com.fei.root.router;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by PengFeifei on 17-7-10.
 */

public class AppContext extends MultiDexApplication {

    private static Context context;

    public static void init(Context context) {
        AppContext.context = context;
    }

    public static Context getInstance() {
        return context;
    }

}

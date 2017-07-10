package com.fei.root.module;

import com.fei.root.router.AppContext;

/**
 * Created by PengFeifei on 17-7-10.
 */

public class ModuleApp extends AppContext {

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }
}

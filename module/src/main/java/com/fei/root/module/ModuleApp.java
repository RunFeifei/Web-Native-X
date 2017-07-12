package com.fei.root.module;

import com.fei.root.router.GlobalContext;

/**
 * Created by PengFeifei on 17-7-10.
 */

public class ModuleApp extends GlobalContext {

    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
    }
}

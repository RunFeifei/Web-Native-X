package com.fei.root.router;

import com.fei.processor.annotation.RouterItem;

/**
 * Created by PengFeifei on 17-7-10.
 */

public interface Routes {

    @RouterItem(action = "moduleActivity")
    String toModule(String name, int age);

    @RouterItem(action = "main")
    int toMain(String name, int age);

}

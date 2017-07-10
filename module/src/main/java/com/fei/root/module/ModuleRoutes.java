package com.fei.root.module;

import com.fei.processor.annotation.RouterItem;
import com.fei.processor.annotation.RouterKey;
import com.fei.root.router.Routes;

/**
 * Created by PengFeifei on 17-7-11.
 */

public interface ModuleRoutes extends Routes {

    @RouterItem(action = "main")
    Integer toMain(@RouterKey(key = "name") String name, @RouterKey(key = "age") int age);
}

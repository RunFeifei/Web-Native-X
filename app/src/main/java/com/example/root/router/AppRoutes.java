package com.example.root.router;

import com.fei.processor.annotation.RouterItem;
import com.fei.processor.annotation.RouterKey;
import com.fei.root.router.Routes;

/**
 * Created by PengFeifei on 17-7-10.
 */

public interface AppRoutes extends Routes {

    @RouterItem(action = "module2")
    String toModule2(@RouterKey(key = "name") String name, @RouterKey(key = "age") int age);
}

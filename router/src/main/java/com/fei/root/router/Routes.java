package com.fei.root.router;

import com.fei.processor.annotation.RouterItem;
import com.fei.processor.annotation.RouterKey;

/**
 * Created by PengFeifei on 17-7-10.
 */

public interface Routes {

    @RouterItem(action = "module1")
    String toModule1(@RouterKey(key = "name") String name, @RouterKey(key = "age") int age);

}

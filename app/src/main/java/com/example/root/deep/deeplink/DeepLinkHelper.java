package com.example.root.deep.deeplink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by PengFeifei on 17-4-14.
 */

public class DeepLinkHelper {

    public static final String SCHEME = "dianrong";
    public static final String PARAMS = "params";
    public static final String ACTION = "action";

    public static boolean checkScheme(String url) {
        return Uri.parse(url).getScheme().equals(SCHEME);
    }

    public static void jump2nativeDispacher(Activity context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);//action
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
        context.finish();
    }

    public static HashMap<String, String> parseUrl2Map(String url) {
        int position = url.lastIndexOf("?");
        String content = url.substring(position + 1);
        HashMap<String, String> map = new HashMap<>(3);
        String[] strs = content.split("&");
        for (String str : strs) {
            String[] pair = str.split("=");
            if (pair.length != 2) {
                continue;
            }
            map.put(pair[0], pair[1]);
        }
        return map;
    }

    public static void invokebyAction(Activity srcActivity, @NonNull HashMap<String, String> map) {
        String action = map.get(DeepLinkHelper.ACTION);
        if (action == null) {
            srcActivity.finish();
        }
        Method[] methods = srcActivity.getClass().getDeclaredMethods();
        for (Method method : methods) {
            LinkMap linkMap = method.getAnnotation(LinkMap.class);
            if (linkMap != null && linkMap.action().equalsIgnoreCase(action)) {
                method.setAccessible(true);
                try {
                    method.invoke(srcActivity, map);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

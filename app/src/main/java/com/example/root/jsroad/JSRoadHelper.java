package com.example.root.jsroad;

import android.app.Activity;
import android.net.Uri;
import android.webkit.WebView;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by PengFeifei on 17-7-3.
 * only for activity
 */

public class JSRoadHelper {
    private static final String SCHEME = "dianrong";

    private static HashMap<String, HashMap<String, Method>> map = new HashMap<>();

    public static void bindMethods(String name, Activity activity) {
        map.put(name, getRegisteredMethods(activity.getClass()));
    }

    private static HashMap<String, Method> getRegisteredMethods(Class clazz) {
        HashMap<String, Method> hashMap = new HashMap<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            JSRoadMap jsRoadMap = method.getAnnotation(JSRoadMap.class);
            if (jsRoadMap != null && jsRoadMap.map()) {
                hashMap.put(method.getName(), method);
            }
        }
        return hashMap;
    }

    public static String invokeNativeMethod(WebView webView, String jsonMessage) {
        Uri uri = Uri.parse(jsonMessage);
        if (!uri.getScheme().equals(SCHEME)) {
            return null;
        }
        String name = uri.getHost();
        String methodName = uri.getPath().replace("/", "").trim();
        String param = uri.getEncodedQuery();

        if (!map.containsKey(name)) {
            return null;
        }
        HashMap<String, Method> methodHashMap = map.get(name);
        if (methodHashMap == null || !methodHashMap.containsKey(methodName)) {
            return null;
        }
        Method method = methodHashMap.get(methodName);
        if (method == null) {
            return null;
        }

        Object object = null;
        try {
            object = method.invoke(webView.getContext(), param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (object == null) {
            return null;
        }
        String result = (String) object;
        doJsCallback(webView, result);
        return null;
    }

    public static void doJsCallback(WebView webView, String result) {
        final String JS_FORMAT = "javascript:JSRoad.onNativeFinish(%s)";
        final String strUrl = String.format(JS_FORMAT, "'" + result + "'");
        webView.loadUrl(strUrl);
    }

}
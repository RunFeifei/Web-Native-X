package com.example.root.deep.deeplink;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by PengFeifei on 17-4-14.
 */

public class DeepLinkHelper {

    public static final String SCHEME = "dianrong";

    public static boolean checkScheme(String url) {
        return Uri.parse(url).getScheme().equals(SCHEME);
    }

    public static void jump2native(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(new Intent());
    }
}

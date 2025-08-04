package com.forpeople.fuckmax;

import android.content.Context;

public class Utils {
    public static boolean isPackageInstalled(Context context, String pkg) {
        try {
            context.getPackageManager().getPackageInfo(pkg, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

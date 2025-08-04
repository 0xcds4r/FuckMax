package com.forpeople.fuckmax;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String pkgName = intent.getData().getSchemeSpecificPart();
        if ("ru.oneme.app".equals(pkgName)) { // Макс
            Intent service = new Intent(context, RemovalService.class);
            context.startService(service);
        }
    }
}

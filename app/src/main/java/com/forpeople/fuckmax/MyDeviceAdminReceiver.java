package com.forpeople.fuckmax;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {}
    @Override
    public void onDisabled(Context context, Intent intent) {}
}

package com.forpeople.fuckmax;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class RemovalService extends Service {
    private static final String TARGET_PACKAGE = "ru.oneme.app";
    private final Handler handler = new Handler();
    private boolean running = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!running) {
            running = true;
            startLoop();
        }
        return START_STICKY;
    }

    private void startLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isPackageInstalled(RemovalService.this, TARGET_PACKAGE)) {
                    Intent deleteIntent = new Intent(Intent.ACTION_DELETE);
                    deleteIntent.setData(Uri.parse("package:" + TARGET_PACKAGE));
                    deleteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(deleteIntent);
                    handler.postDelayed(this, 5000);
                } else {
                    stopSelf();
                    running = false;
                }
            }
        }, 2000);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

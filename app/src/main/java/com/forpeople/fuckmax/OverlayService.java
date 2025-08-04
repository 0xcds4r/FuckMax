package com.forpeople.fuckmax;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class OverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;
    private static final String TARGET_PACKAGE = "ru.oneme.app";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!Settings.canDrawOverlays(this)) {
            stopSelf();
            return;
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        int layoutFlag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER;

        LayoutInflater inflater = LayoutInflater.from(this);
        overlayView = inflater.inflate(R.layout.overlay_layout, null);

        Button btnDelete = overlayView.findViewById(R.id.btn_delete_overlay);
        btnDelete.setOnClickListener(v -> {
            if (Utils.isPackageInstalled(this, TARGET_PACKAGE)) {
                Intent deleteIntent = new Intent(Intent.ACTION_DELETE);
                deleteIntent.setData(Uri.parse("package:" + TARGET_PACKAGE));
                deleteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(deleteIntent);
                stopSelf();
            }
        });

        windowManager.addView(overlayView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView = null;
        }
    }
}

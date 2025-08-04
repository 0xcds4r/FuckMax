package com.forpeople.fuckmax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TARGET_PACKAGE = "ru.oneme.app";
    private static final int REQUEST_OVERLAY_PERMISSION = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Utils.isPackageInstalled(MainActivity.this, TARGET_PACKAGE)) {
            finish();
            return;
        }

        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
        }

        Button deleteBtn = findViewById(R.id.btn_delete_max);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isPackageInstalled(MainActivity.this, TARGET_PACKAGE)) {
                    Intent deleteIntent = new Intent(Intent.ACTION_DELETE);
                    deleteIntent.setData(Uri.parse("package:" + TARGET_PACKAGE));
                    startActivity(deleteIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Приложение Макс не установлено", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startService(new Intent(this, RemovalService.class));

        Intent overlayIntent = new Intent(this, OverlayService.class);
        startService(overlayIntent);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Разрешение на оверлей обязательно!", Toast.LENGTH_LONG).show();
            } else {
                Intent overlayIntent = new Intent(this, OverlayService.class);
                startService(overlayIntent);
            }
        }
    }
}
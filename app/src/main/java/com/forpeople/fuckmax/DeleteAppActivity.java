package com.forpeople.fuckmax;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteAppActivity extends Activity {

    private static final int REQUEST_DELETE = 1;
    public static final String ACTION_DELETE_RESULT = "com.forpeople.fuckmax.ACTION_DELETE_RESULT";
    public static final String EXTRA_DELETED = "deleted";

    private static final String TARGET_PACKAGE = "ru.oneme.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + TARGET_PACKAGE));
        startActivityForResult(intent, REQUEST_DELETE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean deleted = !Utils.isPackageInstalled(this, TARGET_PACKAGE);
        Intent resultIntent = new Intent(ACTION_DELETE_RESULT);
        resultIntent.putExtra(EXTRA_DELETED, deleted);
        sendBroadcast(resultIntent);
        finish();
    }
}

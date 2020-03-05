package com.presentation_view_android;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class PermissionsActivity extends Activity {

    private final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                REQUEST_READ_EXTERNAL_STORAGE_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
        case REQUEST_READ_EXTERNAL_STORAGE_PERMISSIONS: {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setResult(Activity.RESULT_OK, null);
                finish();
            } else {
                setResult(Activity.RESULT_CANCELED, null);
                finish();
            }
        }
        }
    }
}
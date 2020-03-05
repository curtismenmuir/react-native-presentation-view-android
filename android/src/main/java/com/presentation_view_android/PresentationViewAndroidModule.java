package com.presentation_view_android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.util.ArrayList;
import java.util.List;

public class PresentationViewAndroidModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private ReactApplicationContext reactContext;
    private Callback success;
    private Callback failure;
    private final int PRESENTATION_ACTIVITY_REQUEST = 1;
    private final int OVERLAY_PERMISSIONS_REQUEST = 2;
    private final int STORAGE_PERMISSIONS_REQUEST = 3;

    public PresentationViewAndroidModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "PresentationViewAndroidModule";
    }

    @ReactMethod
    public void requestPermissions(final String packageName, final Callback onSuccess, final Callback onFailure) {
        success = onSuccess;
        failure = onFailure;
        checkOverlayPermissions(OVERLAY_PERMISSIONS_REQUEST, packageName);
    }

    // Currently, no use for this method, but must provide Override when
    // implementing ActivityEventListener
    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSIONS_REQUEST) {
            if (Settings.canDrawOverlays(reactContext)) {
                checkStoragePermissions(STORAGE_PERMISSIONS_REQUEST);
            } else {
                failure.invoke(-1);
            }
        } else if (requestCode == STORAGE_PERMISSIONS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                success.invoke(1);
            } else {
                failure.invoke(-1);
            }
        }
    }

    protected void checkOverlayPermissions(int requestCode, String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(reactContext)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName));
            Activity activity = getCurrentActivity();
            activity.startActivityForResult(intent, requestCode);
        } else {
            checkStoragePermissions(STORAGE_PERMISSIONS_REQUEST);
        }
    }

    protected void checkStoragePermissions(int requestCode) {
        if (ContextCompat.checkSelfPermission(reactContext,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Activity currentActivity = reactContext.getCurrentActivity();
            Intent intent = new Intent(reactContext, PermissionsActivity.class);
            currentActivity.startActivityForResult(intent, requestCode);
        } else {
            success.invoke(1);
        }
    }

    @ReactMethod
    public void presentation(final String displayComponent, final Callback onSuccess, final Callback onFailure) {
        if (displayComponent == null) {
            onFailure.invoke(-1);
        }
        Activity currentActivity = getCurrentActivity();
        Intent intent = new Intent(reactContext, PresentationActivity.class);
        intent.putExtra("displayComponent", displayComponent);
        currentActivity.startActivityForResult(intent, PRESENTATION_ACTIVITY_REQUEST);
    }

}
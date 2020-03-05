package com.presentation_view_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.commonsware.cwac.preso.PresentationFragment;
import com.commonsware.cwac.preso.PresentationHelper;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import java.util.ArrayList;

public class PresentationActivity extends ReactActivity
        implements PresentationHelper.Listener, DefaultHardwareBackBtnHandler {

    private PresentationFragment preso = null;
    private PresentationHelper helper = null;
    private ReactFragmentPresentation reactFragmentPresentation = null;
    private String displayComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_activity);
        helper = new PresentationHelper(this, this);
        Bundle props = getIntent().getExtras();
        if (props != null) {
            displayComponent = props.getString("displayComponent");
        }
        setClickEvents();
    }

    protected void setClickEvents() {
        final Button skipBtn = findViewById(R.id.skipButton);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });

        final Button closeBtn = findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("success", true);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    protected void skip() {
        if (reactFragmentPresentation != null) {
            ReactInstanceManager displayInstanceManager = reactFragmentPresentation.getInstanceManager();
            ReactContext displayContext = displayInstanceManager.getCurrentReactContext();
            displayContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("skip", null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        helper.onResume();
    }

    @Override
    public void onPause() {
        helper.onPause();
        super.onPause();
    }

    @Override
    public void clearPreso(boolean switchToInline) {
        if (preso != null) {
            preso.dismiss();
            preso = null;
        }
    }

    @Override
    public void showPreso(Display display) {
        preso = buildPreso(display);
        preso.show(getFragmentManager(), "preso");
    }

    private PresentationFragment buildPreso(Display display) {
        reactFragmentPresentation = ReactFragmentPresentation.newInstance(this, display, displayComponent);
        return reactFragmentPresentation;
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("success", true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}

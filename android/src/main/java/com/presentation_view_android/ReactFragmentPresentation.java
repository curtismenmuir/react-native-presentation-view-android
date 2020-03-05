package com.presentation_view_android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.commonsware.cwac.preso.PresentationFragment;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

public class ReactFragmentPresentation extends PresentationFragment implements DefaultHardwareBackBtnHandler {

    private ReactRootView reactRootView;
    private ReactInstanceManager reactInstanceManager;
    private String displayComponent;

    public static ReactFragmentPresentation newInstance(Context context, Display display, String displayComponent) {
        ReactFragmentPresentation fragment = new ReactFragmentPresentation();
        fragment.setDisplay(context, display);
        fragment.setDisplayComponent(displayComponent);
        return (fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup group = (ViewGroup) inflater.inflate(R.layout.react_fragment, container, false);
        Activity activity = getActivity();

        reactRootView = new ReactRootView(activity);
        reactInstanceManager = ReactInstanceManager.builder().setApplication(activity.getApplication())
                .setCurrentActivity(activity).setBundleAssetName("index.android.bundle").setJSMainModulePath("index")
                .addPackage(new MainReactPackage()).setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED).build();

        group.addView(reactRootView);
        reactRootView.startReactApplication(reactInstanceManager, displayComponent, getLaunchOptions());
        reactInstanceManager.onHostResume(activity, this);
        return group;
    }

    // Method to set the name of the Display Component to be loaded from JS
    public void setDisplayComponent(String displayComponent) {
        this.displayComponent = displayComponent;
    }

    // Method to return ReactInstanceManager used to create React view.
    public ReactInstanceManager getInstanceManager() {
        return this.reactInstanceManager;
    }

    // Method to send initial props to react-native view
    protected Bundle getLaunchOptions() {
        Bundle opts = new Bundle();
        opts.putBoolean("cast", true);
        return opts;
    }

    // Method to override BackPress Listener
    @Override
    public void invokeDefaultOnBackPressed() {
        getActivity().onBackPressed();
    }

    // Method to override onPause() method
    @Override
    public void onPause() {
        super.onPause();
        if (reactInstanceManager != null) {
            reactInstanceManager.onHostPause(getActivity());
        }
    }

    // Method to override onResume() method
    @Override
    public void onResume() {
        super.onResume();
        if (reactInstanceManager != null) {
            reactInstanceManager.onHostResume(getActivity(), this);
        }
    }

    // Method to override onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (reactInstanceManager != null) {
            reactInstanceManager.onHostDestroy(getActivity());
        }
    }

    public void onBackPressed() {
        if (reactInstanceManager != null) {
            reactInstanceManager.onBackPressed();
        } else {
            getActivity().onBackPressed();
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && reactInstanceManager != null) {
            reactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return getActivity().onKeyUp(keyCode, event);
    }
}

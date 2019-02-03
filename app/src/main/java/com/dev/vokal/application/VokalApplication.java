package com.dev.vokal.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.dev.vokal.utils.Constants;

public class VokalApplication extends Application {

    private static final String TAG = "VokalApplication";
    private static VokalApplication applicationInstance = null;
    public int deviceWidth;
    public int deviceHeight;
    public Typeface droidTypeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
        VokalApplication.getInstance().getDisplayMetrix();
        droidTypeFace = Typeface.createFromAsset(getAssets(), Constants.KEY_FONT_TYPE);
    }

    public static VokalApplication getInstance() {
        return applicationInstance;
    }


    public static Context getAppContext() {
        return applicationInstance.getApplicationContext();
    }

    public void getDisplayMetrix() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

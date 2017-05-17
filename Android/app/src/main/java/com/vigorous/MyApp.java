package com.vigorous;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

/**
 * Created by lxm.
 */

public class MyApp extends Application {
    private static final String TAG = MyApp.class.getSimpleName();

    private static Context sContext;
    public static Context getMyAppContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
            Log.d(TAG,"open strict mode");
        }
    }
}

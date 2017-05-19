package com.vigorous.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.vigorous.MyApp;

/**
 * Created by lxm.
 */

public class SpUtils {

    public static SharedPreferences getSp() {
        return MyApp.getMyAppContext().getSharedPreferences(Constant.SP_NAME, Activity.MODE_PRIVATE);
    }

    public static String get(String key) {
        return getSp().getString(key,null);
    }

    public static void set(String key,String value) {
        SharedPreferences.Editor editor = getSp().edit();
        editor.putString(key,value);
        editor.apply();
    }
}

package runsystem.net.global_hr;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import runsystem.net.global_hr.common.Constant;


public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "#onCreate");
        mInstance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory");
    }

    public SharedPreferences getUserDataPreferences() {
        return getSharedPreferences(Constant.PREF_FILE_NAME_USER_DATA,
                Context.MODE_PRIVATE);
    }

    public void remove(String key) {
        SharedPreferences sp = getUserDataPreferences();
        sp.edit().remove(key).commit();
    }

    public void putString(String key, String value) {
        SharedPreferences sp = getUserDataPreferences();
        sp.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        SharedPreferences sp = getUserDataPreferences();
        return sp.getString(key, "");
    }

    public void putInt(String key, int value) {
        SharedPreferences sp = getUserDataPreferences();
        sp.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        SharedPreferences sp = getUserDataPreferences();
        return sp.getInt(key, 0);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences sp = getUserDataPreferences();
        sp.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        SharedPreferences sp = getUserDataPreferences();
        return sp.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean initialValue) {
        SharedPreferences sp = getUserDataPreferences();
        return sp.getBoolean(key, initialValue);
    }

}

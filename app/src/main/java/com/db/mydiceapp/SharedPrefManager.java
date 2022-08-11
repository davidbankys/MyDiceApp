package com.db.mydiceapp;

import android.content.Context;
import android.content.SharedPreferences;

//Manage Shared Pref from this class
public class SharedPrefManager {
    private static boolean isInit = false;
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;
    public final static String PREF_NAME = "Data_Save";


    /**
     * init AndroidSharedPreferences ,must be called before use
     * AndroidSharedPreferences
     *
     * @param context
     */

    public static void init(Context context) {
        if (isInit)
            return;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        isInit = true;
    }
    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public static String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }
    public static int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }
    public static void RemoveAllData() {
        editor.clear().commit();
    }
}

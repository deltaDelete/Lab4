package ru.deltadelete.lab5.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    public static final String PREFERENCES_NAME = "Lab7Prefs";

    public static void putString(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putBool(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public static void putInt(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static void putFloat(Context context, String key, float value) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        return prefs.getString("key", "");
    }

    public static int getInt(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        return prefs.getInt("key", 0);
    }

    public static float getFloat(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        return prefs.getFloat("key", 0f);
    }

    public static boolean getBool(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFERENCES_NAME,
                Context.MODE_PRIVATE
        );
        return prefs.getBoolean("key", false);
    }
}

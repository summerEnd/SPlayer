package com.splayer.util;

import android.util.Log;

/**
 * Created by acer on 2014/9/28.
 */
public class SLog {
    private final static String TAG = "SLOG";
    private static boolean isLogAble = true;

    public static void setIsLogAble(boolean isLogAble) {
        SLog.isLogAble = isLogAble;
    }

    public static final void e(String msg) {
        e(TAG, msg);
    }

    public static final void w(String msg) {
        w(TAG, msg);
    }

    public static final void d(String msg) {
        d(TAG, msg);
    }

    public static final void i(String msg) {
        i(TAG, msg);
    }

    public static final void e(String tag, String msg) {
        if (isLogAble) Log.e(tag, msg);
    }

    public static final void w(String tag, String msg) {
        if (isLogAble) Log.w(tag, msg);
    }

    public static final void i(String tag, String msg) {
        if (isLogAble) Log.i(tag, msg);
    }

    public static final void d(String tag, String msg) {
        if (isLogAble) Log.d(tag, msg);
    }
}

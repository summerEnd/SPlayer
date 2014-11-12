package com.splayer.util;

import android.content.Context;

/**
 * Created by acer on 2014/11/12.
 */
public class ContextUtil {
    private static Context context;

    public static final void init(Context context) {
        ContextUtil.context = context;
    }
}

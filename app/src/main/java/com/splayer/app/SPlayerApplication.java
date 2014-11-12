package com.splayer.app;

import android.app.Application;

import com.splayer.util.ContextUtil;
import com.splayer.util.SLog;

import tour.android.com.myapplication.R;

public class SPlayerApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtil.init(this);
        SLog.setIsLogAble("1".equals(getString(R.string.debug)));
    }
}

package com.guikai.yigou;

import android.app.Application;

import com.guikai.latte.app.Latte;

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}

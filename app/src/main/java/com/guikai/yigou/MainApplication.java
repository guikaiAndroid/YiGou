package com.guikai.yigou;

import android.app.Application;

import com.guikai.latte.global.Latte;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}

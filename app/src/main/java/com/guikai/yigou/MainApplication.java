package com.guikai.yigou;

import android.app.Application;

import com.guikai.latte.app.Latte;
import com.guikai.latte.icon.FontEcModule;
import com.guikai.latte.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://mock.fulingjie.com/mock/api/")
                .withWeChatAppId("Your apply App_id")
                .withWeChatAppSecret("Your apply Secret_id")
                .configure();
    }
}

package com.guikai.yigou;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.guikai.latte.app.Latte;
import com.guikai.latte.icon.FontEcModule;
import com.guikai.latte.net.interceptors.DebugInterceptor;
import com.guikai.latte.util.callback.CallbackManager;
import com.guikai.latte.util.callback.CallbackType;
import com.guikai.latte.util.callback.IGlobalCallback;
import com.guikai.yigou.event.ShareEvent;
import com.guikai.yigou.event.TestEvent;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
//                .withApiHost("http://192.168.0.109/RestServer/data/")
                .withApiHost("http://mock.fulingjie.com/mock-android/api/")
                .withWeChatAppId("Your apply App_id")
                .withWeChatAppSecret("Your apply Secret_id")
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                .withWebEvent("share",new ShareEvent())
                .configure();

        //开启极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //一键分享初始化
        MobSDK.init(this);

        //子模块关闭推送 反向控制主模块关闭推送 接口实现
        CallbackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplicationContext());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplicationContext())) {
                            JPushInterface.stopPush(Latte.getApplicationContext());
                        }
                    }
                });
    }
}

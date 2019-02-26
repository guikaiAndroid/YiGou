package com.guikai.yigou.event;

import android.annotation.SuppressLint;
import android.webkit.WebView;
import android.widget.Toast;

import com.guikai.latte.web.event.Event;

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        //js事件传递给原生事件
        Toast.makeText(getContext(), "您单击了js事件"+getAction(), Toast.LENGTH_LONG).show();
        //原生事件 传递给 js
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
         return null;
    }
}

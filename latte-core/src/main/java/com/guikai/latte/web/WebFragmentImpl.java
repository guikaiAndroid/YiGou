package com.guikai.latte.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guikai.latte.web.chromeclient.WebChromeClientImpl;
import com.guikai.latte.web.client.WebViewClientImpl;
import com.guikai.latte.web.route.RouteKeys;
import com.guikai.latte.web.route.Router;

public class WebFragmentImpl extends WebFragment {

    private IPageLoadListener mIPageLoadListener = null;

    public static WebFragmentImpl create(String url) {
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebFragmentImpl fragment = new WebFragmentImpl();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View root) {
        if (getUrl() != null) {
            //用原生的方式模仿Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getUrl());
        }
    }

    @Override
    public WebView initWebView(WebView webView) {
        //设置一下webView属性
        return new WebViewInitializer().createWebView(webView);
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientImpl();
    }
}

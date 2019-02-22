package com.guikai.latte.web.client;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guikai.latte.app.Latte;
import com.guikai.latte.util.log.LogUtils;
import com.guikai.latte.web.IPageLoadListener;
import com.guikai.latte.web.WebFragment;

public class WebViewClientImpl extends WebViewClient {

    private final WebFragment FRAGMENT;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebFragment fragment) {
        this.FRAGMENT = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.d("shouldOverrideUrlLoading", url);
        return super.shouldOverrideUrlLoading(view, url);
    }



    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

    }
}

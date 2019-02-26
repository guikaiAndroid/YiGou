package com.guikai.latte.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guikai.latte.app.Latte;
import com.guikai.latte.ui.FragmentLoader;
import com.guikai.latte.util.log.LogUtils;
import com.guikai.latte.web.IPageLoadListener;
import com.guikai.latte.web.WebFragment;
import com.guikai.latte.web.route.Router;

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

    //拦截关键方法，当点击WebView链接时，我们打开新的fragment加载
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtils.e("shouldOverrideUrlLoading", url);
        return Router.getInstance().handleWebUrl(FRAGMENT,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        FragmentLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentLoader.stopLoading();
            }
        }, 500);
    }

}

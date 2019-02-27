package com.guikai.latte.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.web.WebFragment;
import com.guikai.latte.web.WebFragmentImpl;
import com.guikai.latte.web.WebViewFragment;

public class Router {

    private Router() {
    }

    //线程安全的惰性单例
    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebFragment fragment, String url) {

        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(fragment.getContext(), url);
            return true;
        }
//        final LatteFragment topFragment = fragment.getTopFragment();
//        final WebFragmentImpl webFragment = WebFragmentImpl.create(url);
//        topFragment.getSupportDelegate().start(webFragment);
        final LatteFragment topFragment = fragment.getTopFragment();
        final WebViewFragment secondActivity = WebViewFragment.newInstance(url);
        topFragment.getSupportDelegate().start(secondActivity);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null !");
        }
    }

    //加载本地网页
    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(WebFragment fragment, String url) {
        loadPage(fragment.getWebView(), url);
    }

    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}

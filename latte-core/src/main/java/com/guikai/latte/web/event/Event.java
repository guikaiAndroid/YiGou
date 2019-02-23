package com.guikai.latte.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.guikai.latte.fragments.LatteFragment;
import com.guikai.latte.web.WebFragment;

public abstract class Event implements IEvent {

    private Context mContent = null;
    private String mAction = null;
    private WebFragment mFragment = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Context getContext() {
        return mContent;
    }

    public WebView getWebView(){
        return mFragment.getWebView();
    }

    public void setContext(Context mContent) {
        this.mContent = mContent;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public LatteFragment getFragment() {
        return mFragment;
    }

    public void setFragment(WebFragment fragment) {
        this.mFragment = fragment;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}

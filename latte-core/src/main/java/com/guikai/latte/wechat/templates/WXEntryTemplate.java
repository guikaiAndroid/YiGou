package com.guikai.latte.wechat.templates;

import com.guikai.latte.wechat.BaseWXEntryActivity;
import com.guikai.latte.wechat.LatteWeChat;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInsuccess(userInfo);
    }
}

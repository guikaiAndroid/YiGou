package com.guikai.yigou.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.guikai.latte.web.event.Event;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Anding on 2019/3/28 23:19
 * Note: 分享的事件
 */

public class ShareEvent extends Event {
    @Override
    public String execute(String params) {
        LogUtils.d("ShareEvent", params);

        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String title = object.getString("title");
        final String url = object.getString("url");
        final String imageUrl = object.getString("imageUrl");
        final String text = object.getString("text");

        final OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(text);
        oks.setImageUrl(imageUrl);
        oks.setUrl(url);
        oks.show(getContext());
        oks.show(getContext());

        return null;
    }
}

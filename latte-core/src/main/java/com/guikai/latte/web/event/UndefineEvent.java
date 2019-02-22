package com.guikai.latte.web.event;

import com.guikai.latte.util.log.LogUtils;

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        LogUtils.e("UndefineEvent", params);
        return null;
    }
}

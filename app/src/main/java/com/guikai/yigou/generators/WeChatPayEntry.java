package com.guikai.yigou.generators;


import com.annotations.annotations.PayEntryGenerator;
import com.guikai.latte.wechat.templates.WXPayEntryTemplate;

@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.guikai.yigou",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {

}

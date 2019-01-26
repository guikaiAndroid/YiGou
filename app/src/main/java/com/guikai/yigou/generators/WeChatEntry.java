package com.guikai.yigou.generators;

import com.annotations.annotations.EntryGenerator;
import com.guikai.latte.wechat.templates.WXEntryTemplate;

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.guikai.yigou",
        entryTemplate =  WXEntryTemplate.class
)
public interface WeChatEntry {

}

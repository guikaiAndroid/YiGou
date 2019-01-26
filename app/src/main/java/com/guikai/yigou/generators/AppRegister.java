package com.guikai.yigou.generators;


import com.annotations.annotations.AppRegisterGenerator;
import com.guikai.latte.wechat.templates.AppRegisterTemplate;

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.guikai.yigou",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {

}

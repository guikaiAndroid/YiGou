package com.guikai.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.guikai.latte.app.Latte;

/**
 * Created by Anding on 2019/1/15 22:12
 * Note: 测量的工具类 建议方法采用public static关键字声明方法
 */

public class DimeUtil {

    //得到屏幕的宽
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    //得到屏幕的高
    public static int getScreenHeight() {
        final Resources resources = Latte.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}

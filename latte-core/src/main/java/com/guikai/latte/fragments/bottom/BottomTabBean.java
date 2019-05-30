package com.guikai.latte.fragments.bottom;

/**
 * Created by Anding on 2019/1/27 14:48
 * Note: 底部导航bean
 */

public final class BottomTabBean {

    private final CharSequence ICON;
    private final CharSequence FILL_ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence fill_icon, CharSequence title) {
        this.ICON = icon;
        this.FILL_ICON = fill_icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getFillIcon() {
        return FILL_ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
